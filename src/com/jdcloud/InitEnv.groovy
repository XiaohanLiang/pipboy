#!/usr/bin/env groovy
package com.jdcloud
import java.io.File;

class InitEnv {

    String JenkinsWorkSpace
    String UserWorkSpace
    String ArtifactSpace
    String MetaSpace
    String CacheSpace
    String RuntimeEnv
    Script script
    def e

    InitEnv(def env,def s){
        this.JenkinsWorkSpace = env.JenkinsWorkSpace
        this.UserWorkSpace = env.UserWorkSpace
        this.ArtifactSpace = env.ArtifactSpace
        this.MetaSpace = env.MetaSpace
        this.CacheSpace = env.CacheSpace
        this.RuntimeEnv = env.RuntimeEnv
        this.script = s
        this.e = env
    }

    def CreatePath(){
        createPath(this.JenkinsWorkSpace)
        createPath(this.CacheSpace)
        createPath(this.UserWorkSpace)
        createPath(this.ArtifactSpace)
        createPath(this.MetaSpace)
    }
    def createPath(String exp){
        File f = new File(exp)
        f.mkdir()
    }
    def CreateFile(String exp){
        def newFile = new File(exp)
        newFile.createNewFile()
    }

    def RecordRegionInfo(){
        File f = new File(this.RuntimeEnv)
        f << ReadFile("/var/tmp/REGION_ID") << "\n"
    }

    def GitInit(){

        this.script.echo "Command Run"
        String setConfigCommand = "git config --local --unset credential.helper"
        String setCredentialCmd = "git config credential.helper 'store --file="+ this.MetaSpace +".git-credentials'"

        this.script.dir(this.UserWorkSpace){
            this.script.echo "Entering UserWorkSpace"
            def setConfig = setConfigCommand.execute()
            def setCredential = setCredentialCmd.execute()
        }

    }

    def Cleaning(String pattern) {

        this.script.dir(this.UserWorkSpace){
            this.script.deleteDir()
        }

        this.script.dir(this.MetaSpace){
            this.script.deleteDir()
        }

    }

    def ReadFile(String filePath) {
        File file = new File(filePath)
        return file.text
    }

    def CheckParameters(){

        checkParametersNonNil(this.e.JenkinsWorkSpace)
        checkParametersNonNil(this.e.ScmUrl)
        checkParametersNonNil(this.e.ScmBranch)
        checkParametersNonNil(this.e.CommitID)
        checkParametersNonNil(this.e.ScmCredential)
        checkParametersNonNil(this.e.Yaml)
        checkParametersNonNil(this.e.UploadArtifact)
        checkParametersNonNil(this.e.CompileModuleName)
        checkParametersNonNil(this.e.OutputSpace)
        checkParametersNonNil(this.e.OssBucketName)
        checkParametersNonNil(this.e.OssBucketpath)
        checkParametersNonNil(this.e.OssBucketEndpoint)
        checkParametersNonNil(this.e.OssAccessKey)
        checkParametersNonNil(this.e.OssSecretKey)

        checkFileExists(this.e.JenkinsWorkSpace)
        checkFileExists(this.e.UserWorkSpace)
        checkFileExists(this.e.ArtifactSpace)
        checkFileExists(this.e.MetaSpace)
        checkFileExists(this.e.CacheSpace)
        checkFileExists(this.e.RuntimeEnv)

    }
    def checkParametersNonNil(String s){
        if (s.length()==0){
            this.script.error("Failed, parameter is supposed to be non-nil")
        }
    }
    def checkFileExists(String d){
        def dir = new File(d)
        if (!dir.exists()){
            this.script.error("Directory " + d + " not exists while supposed to")
        }
    }

    def Execute(){

        CheckParameters()

        Cleaning()

        CreatePath()

        CreateFile(this.RuntimeEnv)

        RecordRegionInfo()
    }
}
