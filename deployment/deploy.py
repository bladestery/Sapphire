#!/usr/bin/python

import os, sys
import subprocess
from time import sleep
import atexit
import signal
from app import *
import getpass
import json
import os

ssh_cmd = "ssh"

#log_folder = "/bigraid/users/" + getpass.getuser() + "/sapphire/logs"
#log_folder = "~/Sapphire/deployment/logs"


def run_cmd(cmd):
    print reduce(lambda x, y: x + " " + y, cmd, "")
    return subprocess.Popen(cmd)

def parse_server_config(server_file):
    f = open(server_file,"r")
    config = json.JSONDecoder().decode(f.read())
    return config


def start_oms(oms, server_file, cp_app, cp_sapphire, p_log):
    hostname = oms["hostname"]
    port = oms["port"]

    print 'Starting OMS on '+hostname+":"+port
    #cmd = [ssh_cmd, hostname, android_export]
    cmd = ['java']
    cmd += ['-Djava.util.logging.config.file=\"' + p_log + '\"']
    cmd += ['-cp']
    cmd += [cp_app]# + ':' + cp_sapphire]
    cmd += ['sapphire.oms.OMSServerImpl']
    cmd += [hostname, port, app_class]
    #cmd += [">", log_folder+"/oms-log."+hostname+"."+port, "2>&1 &"]
    f = open("/home/ubuntu/Sapphire/deployment/logs/oms-log."+hostname+"."+port, 'w+')
    subprocess.Popen(cmd, stdout=f, stderr=f)

    sleep(2)

def start_servers(servers, oms, cp_app, cp_sapphire, p_log):
    for s in servers:
        hostname = s["hostname"]
        port = s["port"]
        print "Starting kernel server on "+hostname+":"+port

        # /bin/classes.dex is generated after you try to run an android app from eclipse_adt
        #cmd = [ssh_cmd, hostname, android_export]
        cmd = ['java']
        cmd += ['-Djava.util.logging.config.file=\"' + p_log + '\"']
        cmd += ['-cp']
        cmd += [cp_app]# + ':' + cp_sapphire]
        cmd += ['sapphire.kernel.server.KernelServerImpl']
        cmd += [hostname, port, oms["hostname"], oms["port"]]
        
        #cmd += [">", log_folder+"/log."+hostname+"."+port, "2>&1 &"]
        f = open("/home/ubuntu/Sapphire/deployment/logs/log."+hostname+"."+port, 'w+')
        subprocess.Popen(cmd, stdout=f, stderr=f)
    
    sleep(2)


def start_clients(clients, oms, cp_app, cp_sapphire, p_log):

    for client in clients:
        hostname = client["hostname"]
        port = client["port"]
        print 'Starting App on '+hostname+":"+port

        # /bin/classes.dex is generated after you try to run an android app from eclipse_adt
        #cmd = [ssh_cmd, hostname, android_export]
        cmd = ['java']
        cmd += ['-Djava.util.logging.config.file=\"' + p_log + '\"']
        cmd += ['-cp']
        cmd += [cp_app]# + ':' + cp_sapphire]
        cmd += [app_client, oms["hostname"], oms["port"], hostname, port]
        #cmd += [">", log_folder+"/client-log."+hostname+"."+port, "2>&1"]
        run_cmd(cmd)

if __name__ == '__main__':
    #try:  
    #    android_home = os.environ["ANDROID_BUILD_TOP"]
    #    android_export = "ANDROID_BUILD_TOP="+android_home
    #except KeyError: 
    #    print "ANDROID_BUILD_TOP is not set - should have been set while building android"
    #    sys.exit()

    cp_app =  '/home/ubuntu/Android/Sdk/platforms/android-17/android.jar:/home/ubuntu/Android/Sdk/platforms/android-17/data/res:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/classes/debug:/home/ubuntu/Android/Sdk/extras/android/m2repository/com/android/support/support-v4/18.0.0/support-v4-18.0.0.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/rmi.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/app/build/outputs/apk/app-debug/classes-dex2jar.jar'
    cp_sapphire = '~/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/outputs/aar/classes.jar'
    #cp_app = 'classes-dex2jar.jar'
    #cp_sapphire = 'classes.jar'
    p_log = '~/Sapphire/sapphire/logging.properties'

    server_file = "servers.json"
    config = parse_server_config(server_file)
    start_oms(config["oms"], os.path.abspath(server_file), cp_app, cp_sapphire, p_log)
    start_servers(config["servers"], config["oms"], cp_app, cp_sapphire, p_log)
    #start_clients(config["clients"], config["oms"], cp_app, cp_sapphire, p_log)
    
