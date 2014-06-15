def projectDir = targetDir as File
def props = [:]

def askQuestions = {
    props.group = ask("Define value for 'group' [org.example]: ", "org.example", "group")
    props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
    props.browserExecutable = ask("Location of Browser executable [/usr/bin/firefox]: ", "/usr/bin/firefox", "browserExecutable")
}

def processTemplates = {
    processTemplates "build.gradle", props
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
    def spockSpecDestination = "src/test/groovy/${props.group.replaceAll('\\.','/')}/SampleSpecification.groovy"

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

