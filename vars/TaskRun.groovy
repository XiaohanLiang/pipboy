#!/usr/bin/env groovy
import com.jdcloud.*

/**
 *  This function is expected to output a structure namely
 *  `Tasks` containing Key-Value pair
 *
 *  Since this will be a structure. It must be somehow
 *  Connected to Java-Class, yes, output a Java-Class instance
 *
 */

def call(def pathToYaml){

    // Parse yaml and make it Java class
    echo "1"
    Cmds setting = new Cmds("/root/build.yaml")
    println(setting)
    echo "2"
//    echo "2"
//    cmds.SetUp(pathToYaml)
//    echo "3"

    // Generate exporting commands
//    cmds.PreprareEnvs()
//
    // Running inside Docker
//    withDockerContainer(args:"", image:params.buildImage) {
//        cmds.Execute()
//    }
}