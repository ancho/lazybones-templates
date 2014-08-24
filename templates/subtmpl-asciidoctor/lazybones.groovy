@Grab('commons-io:commons-io:2.4')
import org.apache.commons.io.FileUtils

def props = [:]

props.version = parentParams.version
props.projectName = parentParams.name
props.author = ask("Give me a name of an Author: ", "", "author")
props.email = ask("Give me an email Address: ", "", "email")

def processTemplates = {
    def templateFile = "doc/src/asciidoc/index.adoc"
    processTemplates(templateFile, props)
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
