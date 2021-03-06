@Grab('commons-io:commons-io:2.4')
import org.apache.commons.io.FileUtils

def props = [:]

props.version = parentParams.version
props.projectName = parentParams.name
props.author = ask("Give me a name of an Author: ", "", "author")
props.email = ask("Give me an email Address: ", "", "email")
props.asciidoctorVersion = ask("Set asciidoctor version [1.5.4]: ", "1.5.4", "asciidoctorVersion")
props.diagram = ask("Do you want to enable asciidoctor-diagram plugin? (default: no) ","","diagram")

def processTemplates = {
    def templateFile = "doc/src/docs/asciidoc/index.adoc"
    processTemplates(templateFile, props)
    def propertiesFile = "doc/gradle.properties"
    processTemplates(propertiesFile, props)
    def asciidocGradleFile = "doc/gradle/asciidoctor.gradle"
    processTemplates(asciidocGradleFile, props)
}

def copyDir = {
    def sourcePath = new File("$templateDir/doc")
    def destination = new File("$projectDir/doc")

    FileUtils.copyDirectory(sourcePath, destination)
}

def addSubproject = {
    def subProjectName = "doc"
    def settingsFile = new File("$projectDir/settings.gradle")

    settingsFile.withWriterAppend {
        it << "include '$subProjectName'\n"
    }
}

processTemplates()
copyDir()
addSubproject()
