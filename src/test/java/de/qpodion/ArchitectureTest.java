package de.qpodion;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import de.qpodion.validator.GenericValidator;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {
    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("de.qpodion");

    @Test
    void controllersResideInControllerPackage() {
        ArchRule rule = classes()
                .that()
                .areAnnotatedWith(RestController.class)
                .or()
                .haveNameMatching(".*Controller$")
                .should()
                .resideInAPackage("de.qpodion.controller");

        rule.check(importedClasses);
    }

    @Test
    void servicesOnlyAccessedByControllersAndServices() {
        ArchRule rule = classes()
                .that()
                .resideInAPackage("de.qpodion.service")
                .should()
                .onlyBeAccessed()
                .byAnyPackage("de.qpodion.controller", "de.qpodion.service");

        rule.check(importedClasses);
    }

    @Test
    void layeredArchitectureGiven() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("de.qpodion.controller")
                .layer("Service").definedBy("de.qpodion.service")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service");

        rule.check(importedClasses);
    }

    @Test
    void validatorsExtendValidatorInterface() {
        ArchRule rule = classes()
                .that()
                .implement(GenericValidator.class)
                .should()
                .haveSimpleNameEndingWith("Validator");

        rule.check(importedClasses);
    }

    @Test
    void doNotThrowGenericExceptions() {
        ArchRule rule = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

        rule.check(importedClasses);
    }
}
