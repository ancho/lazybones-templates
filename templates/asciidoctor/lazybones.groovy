if ( hasFeature("scmExclusions") ) {
    scmExclusions   ".lazybones",
                    ".gradle",
                    ".idea",
                    '*.iws',
                    '*.ipr',
                    '*.iml',
                    'build/'
}

def props = [:]
def projectName = projectDir.getName()

props.projectName = ask("Set Project Name [$projectName]: ", projectName, "name")
props.version = ask("Set Version [0.1]: ", "0.1", "version")
props.author = ask("Give me a name of an Author: ", "", "author")
props.email = ask("Give me an email Address: ", "", "email")
props.asciidoctorVersion = ask("Set asciidoctor version [1.5.4]: ", "1.5.4", "asciidoctorVersion")
props.diagram = ask("Do you want to enable asciidoctor-diagram plugin? (default: no) ","","diagram")

def files = [
  "src/docs/asciidoc/index.adoc",
  "gradle/asciidoctor.gradle",
  "gradle.properties"
]


files.each{ file ->

    processTemplates(file, props)
}
