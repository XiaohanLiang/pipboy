#!/usr/bin/env groovy
import com.jdcloud.*

def call(def env) {

    env.JenkinsWorkSpace = env.WORKSPACE
    env.UserWorkSpace = env.WORKSPACE + "/workspace/"
    env.ArtifactSpace = env.WORKSPACE + "/artifact/"
    env.MetaSpace = env.WORKSPACE + "/meta/"
    env.CacheSpace = env.WORKSPACE + "/cache/"
    env.RuntimeEnv = env.WORKSPACE + "/meta/buildRuntimeEnv"
    env.Dockerfile = env.WORKSPACE + "/workspace/Dockerfile"
    env.JdcloudYaml = env.WORKSPACE + "/workspace/jdcloud-build.yml"
    env.BuildYaml = env.WORKSPACE + "/workspace/build.yml"

    def initiating = new InitEnv(env, this)
    initiating.Execute()
}