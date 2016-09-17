//def projectDir = targetDir as File
def props = [:]

def projectName = projectDir.getName()

def askQuestions = {
    props.name = ask("Projectname [$projectName]: ", projectName, "name" )
    props.reponame = ask("Repository name [$projectName]: ", projectName, 'reponame')
    props.group = ask("Define value for 'group' [de.calmdevelopment]: ", "de.calmdevelopment", "group")
    props.version = ask("Define value for 'version' [0.1]: ", "0.1", "version")
    props.description = ask("Add a short description: ","A short description")
    props.browserExecutable = ask("Location of Browser executable [/usr/bin/firefox]: ", "/usr/bin/firefox", "browserExecutable")
    def website = "https://github.com/ancho/$props.reponame"
    def issues = "$website/issues"
    props.websiteUrl = ask("Website [$website]: ", website, "websiteUrl")
    props.issuesUrl = ask("Issues [$issues]:  ", issues, "issuesUrl")
    props.vcsUrl = ask("VCS [$website]: ", website, "vcsUrl")
    def year = new Date()[Calendar.YEAR]
    props.inceptionYear = ask("Inception year [$year]: ", year as String , "inceptionYear")
    props.vendor = ask("Vendor [calmdevelopment]: ", "calmdevelopment", "vendor")
    props.bintray_licenses = ask("Bintray licenses [apache2]: ", "apache2", "bintray_licenses")
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
    copySkeleton( 'gradle.properties', 'gradle.properties' )
}

def deleteSkeletonDir = {
    new File(projectDir, "skel").deleteDir()
}


/** Here we go **/
askQuestions()
processTemplates()
copySkeletons()
deleteSkeletonDir()