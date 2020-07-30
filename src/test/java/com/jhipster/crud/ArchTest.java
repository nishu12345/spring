package com.jhipster.crud;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.jhipster.crud");

        noClasses()
            .that()
                .resideInAnyPackage("com.jhipster.crud.service..")
            .or()
                .resideInAnyPackage("com.jhipster.crud.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.jhipster.crud.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
