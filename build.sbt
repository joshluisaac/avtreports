libraryDependencies ++= Seq(
  dependencies.jasperReportsCore,
  dependencies.javaJna,
  dependencies.javaJnaPlatform,
  dependencies.jackson % Test,
  testDependencies.junit,
  testDependencies.junitInterface
)

lazy val reports = (project in file("."))
  .settings(
    keys.subOrganization := "transact",
    (unmanagedResourceDirectories in Compile) += (sourceDirectory in Compile).value / "jasper"
  )
