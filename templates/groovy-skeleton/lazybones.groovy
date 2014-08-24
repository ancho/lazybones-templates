//def projectDir = targetDir as File
def props = [:]

def projectName = projectDir.getName()

def askQuestions = {
    props.group = ask("Define value for 'group' [org.example]: ", "org.example", "group")
    props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
    props.browserExecutable = ask("Location of Browser executable [/usr/bin/firefox]: ", "/usr/bin/firefox", "browserExecutable")
    props.name = ask("Projectname [$projectName]: ", projectName, "name" )
}

def processTemplates = {
    processTemplates "build.gradle", props
    processTemplates "settings.gradle", props
    processTemplates "skel/*", props
}


def copySkeleton = { String skeletonName, String path ->

    String skeleton = "skel/$skeletonName"
    File target = new File(projectDir, path)

    target.parentFile.mkdirs()
    new File( projectDir, skeleton ).renameTo( target )
}

def copySkeletons = {
    def spockSpecTemplate = "GroovyTest.tmpl"
    def spockSpecDestination = "src/test/groovy/${props.group.replace('.','/')}/SampleSpecification.groovy"

    copySkeleton( spockSpecTemplate, spockSpecDestination )
}

def deleteSkeletonDir = {
    new File(projectDir, "skel").deleteDir()
}


/** Here we go **/
askQuestions()
processTemplates()
copySkeletons()
deleteSkeletonDir()

