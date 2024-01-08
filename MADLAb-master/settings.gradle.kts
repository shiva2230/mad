pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MAD Lab"
include(":CO1:app1")
include(":CO1:app2")
include(":CO1:app3")
include(":CO2:app3")
include(":CO2:app1")
include(":CO3:app5")
include(":CO3:app2")
include(":CO3:app3")
include(":CO3:app4")
include(":CO2:app4")
include(":CO2:app2")
include(":CO1:app4")
include(":CO4:app2")
include(":CO5:app1_2")
include(":CO4:app1")
include(":CO4:app3")
include(":CO4:app4")
