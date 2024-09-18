package roommate.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import roommate.RoommateApplication;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;


@AnalyzeClasses(packagesOf = RoommateApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class OnionArc {
    @ArchTest
    ArchRule rule = onionArchitecture()
            .domainModels("..roommate.domain.model..")
            .domainServices("..roommate.domain.model..")
            .applicationServices("..roommate.application..")
            .adapter("web", "..roommate.web..")
            .adapter("persistence", "..roommate.db..")
            .adapter("rest", "..roommate.rest..");
    @ArchTest
    ArchRule onion2 = noClasses()
            .that()
            .resideInAPackage("roommate.domain..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("org.springframework..");

    @ArchTest
    ArchRule variablesInControllerShouldBePrivate = fields()
            .that()
            .areDeclaredInClassesThat()
            .areAnnotatedWith(Controller.class)
            .should()
            .bePrivate();

    @ArchTest
    ArchRule allClassesInApplicationServiceShouldBeAnnotatedWithService  = classes()
            .that()
            .resideInAPackage("..application.service..").should().beAnnotatedWith(Service.class);

    @ArchTest
    ArchRule onlyControllerMayAccessController = noClasses()
            .that()
            .resideOutsideOfPackage("..roommate.web..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..roommate.web..").andShould().resideInAPackage("..roommate.web.requestEntities..");

//    @ArchTest
//    ArchRule variablesInClassesInDomainModelShouldHaveGetter = classes()
//            .that()
//            .resideInAPackage("..roommate.domain.model..")
//            .and()
//            .areNotRecords()
//            .should().haveOnlyFinalFields();


}
