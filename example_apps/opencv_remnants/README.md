#
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x00007fc4bd5e7b00, pid=5901, tid=0x00007fc4b195c700
#
# JRE version: Java(TM) SE Runtime Environment (8.0_121-b13) (build 1.8.0_121-b13)
# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.121-b13 mixed mode linux-amd64 compressed oops)
# Problematic frame:
# C  [libopencv_java3.so+0x234b00]  Java_org_opencv_core_Mat_n_1rows+0x0
#
# Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
#
# If you would like to submit a bug report, please visit:
#   http://bugreport.java.com/bugreport/crash.jsp
# The crash happened outside the Java Virtual Machine in native code.
# See problematic frame for where to report the bug.
#

---------------  T H R E A D  ---------------

Current thread (0x00007fc4cc003000):  JavaThread "RMI TCP Connection(5)-192.51.223.52" daemon [_thread_in_native, id=6481, stack(0x00007fc4b185c000,0x00007fc4b195d000)]

siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000062ed7a78

Registers:
RAX=0x00007fc4bd5e7b00, RBX=0x00007fc52e99b2d0, RCX=0x00007fc5400066c0, RDX=0x0000000062ed7a70
RSP=0x00007fc4b19591d8, RBP=0x00007fc4b1959238, RSI=0x00007fc4b1959248, RDI=0x00007fc4cc0031f8
R8 =0x0000000000000000, R9 =0x0000000000000011, R10=0x00007fc530bf1a08, R11=0x00007fc545ab0ea0
R12=0x0000000000000000, R13=0x00007fc52e99b2d0, R14=0x00007fc4b1959260, R15=0x00007fc4cc003000
RIP=0x00007fc4bd5e7b00, EFLAGS=0x0000000000010287, CSGSFS=0x0000000000000033, ERR=0x0000000000000004
  TRAPNO=0x000000000000000e

Top of Stack: (sp=0x00007fc4b19591d8)
0x00007fc4b19591d8:   00007fc530bf1a34 00007fc4b1959238
0x00007fc4b19591e8:   00007fc530bf17c7 00007fc530bf1782
0x00007fc4b19591f8:   00007fc4b19591f8 00007fc52e99b2d0
0x00007fc4b1959208:   00007fc4b1959260 00007fc52ea801d8
0x00007fc4b1959218:   0000000000000000 00007fc52e99b2d0
0x00007fc4b1959228:   0000000000000000 00007fc4b1959258
0x00007fc4b1959238:   00007fc4b19592a8 00007fc530be1d00
0x00007fc4b1959248:   000000076ce101d8 00007fc530beb536
0x00007fc4b1959258:   0000000062ed7a70 000000076d9e1448
0x00007fc4b1959268:   00007fc4b1959268 00007fc52e9971d4
0x00007fc4b1959278:   00007fc4b19592c0 00007fc52ea801d8
0x00007fc4b1959288:   0000000000000000 00007fc52e997200
0x00007fc4b1959298:   00007fc4b1959258 00007fc4b19592c0
0x00007fc4b19592a8:   00007fc4b1959310 00007fc530be1d00
0x00007fc4b19592b8:   0000000000000000 000000076d9e1448
0x00007fc4b19592c8:   000000076da0daa8 00007fc4b19592d0
0x00007fc4b19592d8:   00007fc52e997f95 00007fc4b1959320
0x00007fc4b19592e8:   00007fc52ea801d8 0000000000000000
0x00007fc4b19592f8:   00007fc52e998018 00007fc4b19592c0
0x00007fc4b1959308:   00007fc4b1959320 00007fc4b1959368
0x00007fc4b1959318:   00007fc530be2040 000000076d9e1448
0x00007fc4b1959328:   00007fc4b1959328 00007fc52e44e0ba
0x00007fc4b1959338:   00007fc4b1959378 00007fc52e4f5b98
0x00007fc4b1959348:   00007fc52e87aa80 00007fc52e44e0c8
0x00007fc4b1959358:   00007fc4b1959320 00007fc4b1959378
0x00007fc4b1959368:   00007fc4b19593c8 00007fc530be2040
0x00007fc4b1959378:   000000076d9e1448 000000076da0da60
0x00007fc4b1959388:   00007fc4b1959388 00007fc52e4bf35a
0x00007fc4b1959398:   00007fc4b19593e0 00007fc52e566168
0x00007fc4b19593a8:   00007fc52e87a898 00007fc52e4bf368
0x00007fc4b19593b8:   00007fc4b1959378 00007fc4b19593d8
0x00007fc4b19593c8:   00007fc4b1959428 00007fc530be2040 

Instructions: (pc=0x00007fc4bd5e7b00)
0x00007fc4bd5e7ae0:   8b 02 c1 e8 0e 83 e0 01 c3 0f 1f 80 00 00 00 00
0x00007fc4bd5e7af0:   8b 02 c1 e8 0f 83 e0 01 c3 0f 1f 80 00 00 00 00
0x00007fc4bd5e7b00:   8b 42 08 c3 66 2e 0f 1f 84 00 00 00 00 00 66 90
0x00007fc4bd5e7b10:   8b 02 48 8b 7a 48 4c 63 c1 be 11 22 44 88 31 d2 

Register to memory mapping:

RAX=0x00007fc4bd5e7b00: Java_org_opencv_core_Mat_n_1rows+0 in /usr/local/share/OpenCV/java/libopencv_java3.so at 0x00007fc4bd3b3000
RBX={method} {0x00007fc52e99b2d0} 'n_rows' '(J)I' in 'org/opencv/core/Mat'
RCX=0x00007fc5400066c0 is an unknown value
RDX=0x0000000062ed7a70 is an unknown value
RSP=0x00007fc4b19591d8 is pointing into the stack for thread: 0x00007fc4cc003000
RBP=0x00007fc4b1959238 is pointing into the stack for thread: 0x00007fc4cc003000
RSI=0x00007fc4b1959248 is pointing into the stack for thread: 0x00007fc4cc003000
RDI=0x00007fc4cc0031f8 is an unknown value
R8 =0x0000000000000000 is an unknown value
R9 =0x0000000000000011 is an unknown value
R10=0x00007fc530bf1a08 is at code_begin+808 in an Interpreter codelet
method entry point (kind = native)  [0x00007fc530bf16e0, 0x00007fc530bf1fe0]  2304 bytes
R11=0x00007fc545ab0ea0: <offset 0x9c3ea0> in /usr/lib/jvm/java-8-oracle/jre/lib/amd64/server/libjvm.so at 0x00007fc5450ed000
R12=0x0000000000000000 is an unknown value
R13={method} {0x00007fc52e99b2d0} 'n_rows' '(J)I' in 'org/opencv/core/Mat'
R14=0x00007fc4b1959260 is pointing into the stack for thread: 0x00007fc4cc003000
R15=0x00007fc4cc003000 is a thread


Stack: [0x00007fc4b185c000,0x00007fc4b195d000],  sp=0x00007fc4b19591d8,  free space=1012k
Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
C  [libopencv_java3.so+0x234b00]  Java_org_opencv_core_Mat_n_1rows+0x0
j  org.opencv.core.Mat.rows()I+4
j  org.opencv.core.Mat.toString()Ljava/lang/String;+13
j  java.lang.String.valueOf(Ljava/lang/Object;)Ljava/lang/String;+10
j  java.lang.StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;+2
j  java.util.AbstractCollection.toString()Ljava/lang/String;+51
j  java.lang.String.valueOf(Ljava/lang/Object;)Ljava/lang/String;+10
j  java.lang.StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;+2
j  java.util.AbstractCollection.toString()Ljava/lang/String;+51
j  sapphire.kernel.server.KernelServerImpl.makeKernelRPC(Lsapphire/kernel/common/KernelRPC;)Ljava/lang/Object;+57
v  ~StubRoutines::call_stub
V  [libjvm.so+0x690dd6]  JavaCalls::call_helper(JavaValue*, methodHandle*, JavaCallArguments*, Thread*)+0x1056
V  [libjvm.so+0x99c357]  Reflection::invoke(instanceKlassHandle, methodHandle, Handle, bool, objArrayHandle, BasicType, objArrayHandle, bool, Thread*)+0x5d7
V  [libjvm.so+0x99fa67]  Reflection::invoke_method(oopDesc*, Handle, objArrayHandle, Thread*)+0x147
V  [libjvm.so+0x72447b]  JVM_InvokeMethod+0x26b
j  sun.reflect.NativeMethodAccessorImpl.invoke0(Ljava/lang/reflect/Method;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+0
j  sun.reflect.NativeMethodAccessorImpl.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+100
j  sun.reflect.DelegatingMethodAccessorImpl.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+6
j  java.lang.reflect.Method.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+56
j  sun.rmi.server.UnicastServerRef.dispatch(Ljava/rmi/Remote;Ljava/rmi/server/RemoteCall;)V+209
j  sun.rmi.transport.Transport$1.run()Ljava/lang/Void;+23
j  sun.rmi.transport.Transport$1.run()Ljava/lang/Object;+1
v  ~StubRoutines::call_stub
V  [libjvm.so+0x690dd6]  JavaCalls::call_helper(JavaValue*, methodHandle*, JavaCallArguments*, Thread*)+0x1056
V  [libjvm.so+0x72a09c]  JVM_DoPrivileged+0x27c
j  java.security.AccessController.doPrivileged(Ljava/security/PrivilegedExceptionAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;+0
j  sun.rmi.transport.Transport.serviceCall(Ljava/rmi/server/RemoteCall;)Z+157
j  sun.rmi.transport.tcp.TCPTransport.handleMessages(Lsun/rmi/transport/Connection;Z)V+185
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0()V+680
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.lambda$run$0()Ljava/lang/Void;+1
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler$$Lambda$7.run()Ljava/lang/Object;+4
v  ~StubRoutines::call_stub
V  [libjvm.so+0x690dd6]  JavaCalls::call_helper(JavaValue*, methodHandle*, JavaCallArguments*, Thread*)+0x1056
V  [libjvm.so+0x72a09c]  JVM_DoPrivileged+0x27c
j  java.security.AccessController.doPrivileged(Ljava/security/PrivilegedAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;+0
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run()V+58
j  java.util.concurrent.ThreadPoolExecutor.runWorker(Ljava/util/concurrent/ThreadPoolExecutor$Worker;)V+95
j  java.util.concurrent.ThreadPoolExecutor$Worker.run()V+5
j  java.lang.Thread.run()V+11
v  ~StubRoutines::call_stub
V  [libjvm.so+0x690dd6]  JavaCalls::call_helper(JavaValue*, methodHandle*, JavaCallArguments*, Thread*)+0x1056
V  [libjvm.so+0x6912e1]  JavaCalls::call_virtual(JavaValue*, KlassHandle, Symbol*, Symbol*, JavaCallArguments*, Thread*)+0x321
V  [libjvm.so+0x691787]  JavaCalls::call_virtual(JavaValue*, Handle, KlassHandle, Symbol*, Symbol*, Thread*)+0x47
V  [libjvm.so+0x72cb00]  thread_entry(JavaThread*, Thread*)+0xa0
V  [libjvm.so+0xa75543]  JavaThread::thread_main_inner()+0x103
V  [libjvm.so+0xa7568c]  JavaThread::run()+0x11c
V  [libjvm.so+0x926268]  java_start(Thread*)+0x108
C  [libpthread.so.0+0x8184]  start_thread+0xc4

Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)
j  org.opencv.core.Mat.n_rows(J)I+0
j  org.opencv.core.Mat.rows()I+4
j  org.opencv.core.Mat.toString()Ljava/lang/String;+13
j  java.lang.String.valueOf(Ljava/lang/Object;)Ljava/lang/String;+10
j  java.lang.StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;+2
j  java.util.AbstractCollection.toString()Ljava/lang/String;+51
j  java.lang.String.valueOf(Ljava/lang/Object;)Ljava/lang/String;+10
j  java.lang.StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;+2
j  java.util.AbstractCollection.toString()Ljava/lang/String;+51
j  sapphire.kernel.server.KernelServerImpl.makeKernelRPC(Lsapphire/kernel/common/KernelRPC;)Ljava/lang/Object;+57
v  ~StubRoutines::call_stub
j  sun.reflect.NativeMethodAccessorImpl.invoke0(Ljava/lang/reflect/Method;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+0
j  sun.reflect.NativeMethodAccessorImpl.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+100
j  sun.reflect.DelegatingMethodAccessorImpl.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+6
j  java.lang.reflect.Method.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+56
j  sun.rmi.server.UnicastServerRef.dispatch(Ljava/rmi/Remote;Ljava/rmi/server/RemoteCall;)V+209
j  sun.rmi.transport.Transport$1.run()Ljava/lang/Void;+23
j  sun.rmi.transport.Transport$1.run()Ljava/lang/Object;+1
v  ~StubRoutines::call_stub
j  java.security.AccessController.doPrivileged(Ljava/security/PrivilegedExceptionAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;+0
j  sun.rmi.transport.Transport.serviceCall(Ljava/rmi/server/RemoteCall;)Z+157
j  sun.rmi.transport.tcp.TCPTransport.handleMessages(Lsun/rmi/transport/Connection;Z)V+185
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0()V+680
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.lambda$run$0()Ljava/lang/Void;+1
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler$$Lambda$7.run()Ljava/lang/Object;+4
v  ~StubRoutines::call_stub
j  java.security.AccessController.doPrivileged(Ljava/security/PrivilegedAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;+0
j  sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run()V+58
j  java.util.concurrent.ThreadPoolExecutor.runWorker(Ljava/util/concurrent/ThreadPoolExecutor$Worker;)V+95
j  java.util.concurrent.ThreadPoolExecutor$Worker.run()V+5
j  java.lang.Thread.run()V+11
v  ~StubRoutines::call_stub

---------------  P R O C E S S  ---------------

Java Threads: ( => current thread )
=>0x00007fc4cc003000 JavaThread "RMI TCP Connection(5)-192.51.223.52" daemon [_thread_in_native, id=6481, stack(0x00007fc4b185c000,0x00007fc4b195d000)]
  0x00007fc4c4004800 JavaThread "RMI TCP Connection(4)-192.51.223.52" daemon [_thread_in_native, id=6480, stack(0x00007fc4b195d000,0x00007fc4b1a5e000)]
  0x00007fc4c4003000 JavaThread "RMI TCP Connection(3)-192.51.223.52" daemon [_thread_in_native, id=6479, stack(0x00007fc4b1a5e000,0x00007fc4b1b5f000)]
  0x00007fc4cc001000 JavaThread "RMI TCP Connection(2)-157.82.159.46" daemon [_thread_in_native, id=6476, stack(0x00007fc4f80ad000,0x00007fc4f81ae000)]
  0x00007fc4c4001000 JavaThread "RMI TCP Connection(1)-157.82.159.46" daemon [_thread_in_native, id=6474, stack(0x00007fc4fa012000,0x00007fc4fa113000)]
  0x00007fc54000a800 JavaThread "DestroyJavaVM" [_thread_blocked, id=5906, stack(0x00007fc546bda000,0x00007fc546cdb000)]
  0x00007fc5402dd000 JavaThread "Thread-1" [_thread_blocked, id=5937, stack(0x00007fc4fa113000,0x00007fc4fa214000)]
  0x00007fc5402a5800 JavaThread "RMI TCP Accept-22345" daemon [_thread_in_native, id=5934, stack(0x00007fc4fa23a000,0x00007fc4fa33b000)]
  0x00007fc5402a9000 JavaThread "RMI Reaper" [_thread_blocked, id=5933, stack(0x00007fc4fa33b000,0x00007fc4fa43c000)]
  0x00007fc5402a8000 JavaThread "RMI TCP Accept-0" daemon [_thread_in_native, id=5932, stack(0x00007fc4fa43c000,0x00007fc4fa53d000)]
  0x00007fc54029f000 JavaThread "GC Daemon" daemon [_thread_blocked, id=5930, stack(0x00007fc4fa53d000,0x00007fc4fa63e000)]
  0x00007fc540299800 JavaThread "RMI RenewClean-[157.82.159.46:44024]" daemon [_thread_blocked, id=5929, stack(0x00007fc4fa63e000,0x00007fc4fa73f000)]
  0x00007fc540298000 JavaThread "RMI Scheduler(0)" daemon [_thread_blocked, id=5928, stack(0x00007fc4fa73f000,0x00007fc4fa840000)]
  0x00007fc5400d4000 JavaThread "Service Thread" daemon [_thread_blocked, id=5924, stack(0x00007fc4fb72f000,0x00007fc4fb830000)]
  0x00007fc5400c9000 JavaThread "C1 CompilerThread3" daemon [_thread_blocked, id=5923, stack(0x00007fc50003d000,0x00007fc50013e000)]
  0x00007fc5400c7000 JavaThread "C2 CompilerThread2" daemon [_thread_blocked, id=5922, stack(0x00007fc50013e000,0x00007fc50023f000)]
  0x00007fc5400c5000 JavaThread "C2 CompilerThread1" daemon [_thread_blocked, id=5921, stack(0x00007fc50023f000,0x00007fc500340000)]
  0x00007fc5400c2000 JavaThread "C2 CompilerThread0" daemon [_thread_blocked, id=5920, stack(0x00007fc52c0d0000,0x00007fc52c1d1000)]
  0x00007fc5400c0800 JavaThread "Signal Dispatcher" daemon [_thread_blocked, id=5919, stack(0x00007fc52c1d1000,0x00007fc52c2d2000)]
  0x00007fc54008d000 JavaThread "Finalizer" daemon [_thread_blocked, id=5918, stack(0x00007fc52c2d2000,0x00007fc52c3d3000)]
  0x00007fc540088800 JavaThread "Reference Handler" daemon [_thread_blocked, id=5917, stack(0x00007fc52c3d3000,0x00007fc52c4d4000)]

Other Threads:
  0x00007fc540080800 VMThread [stack: 0x00007fc52c4d4000,0x00007fc52c5d5000] [id=5916]
  0x00007fc5400d7000 WatcherThread [stack: 0x00007fc4fb62e000,0x00007fc4fb72f000] [id=5925]

VM state:not at safepoint (normal execution)

VM Mutex/Monitor currently owned by a thread: None

Heap:
 PSYoungGen      total 74752K, used 12903K [0x000000076cd80000, 0x0000000772080000, 0x00000007c0000000)
  eden space 64512K, 20% used [0x000000076cd80000,0x000000076da19df8,0x0000000770c80000)
  from space 10240K, 0% used [0x0000000770c80000,0x0000000770c80000,0x0000000771680000)
  to   space 10240K, 0% used [0x0000000771680000,0x0000000771680000,0x0000000772080000)
 ParOldGen       total 171008K, used 882K [0x00000006c6800000, 0x00000006d0f00000, 0x000000076cd80000)
  object space 171008K, 0% used [0x00000006c6800000,0x00000006c68dc8d8,0x00000006d0f00000)
 Metaspace       used 7309K, capacity 7592K, committed 7680K, reserved 1056768K
  class space    used 864K, capacity 974K, committed 1024K, reserved 1048576K

Card table byte_map: [0x00007fc53004d000,0x00007fc53081a000] byte_map_base: 0x00007fc52ca19000

Marking Bits: (ParMarkBitMap*) 0x00007fc5460b1c80
 Begin Bits: [0x00007fc500340000, 0x00007fc5041a0000)
 End Bits:   [0x00007fc5041a0000, 0x00007fc508000000)

Polling page: 0x00007fc546cfb000

CodeCache: size=245760Kb used=2198Kb max_used=2198Kb free=243561Kb
 bounds [0x00007fc530bda000, 0x00007fc530e4a000, 0x00007fc53fbda000]
 total_blobs=928 nmethods=541 adapters=301
 compilation: enabled

Compilation events (10 events):
Event: 275.999 Thread 0x00007fc5400c9000 nmethod 535 0x00007fc530dfe250 code [0x00007fc530dfe420, 0x00007fc530dfe9a8]
Event: 275.999 Thread 0x00007fc5400c9000  536       3       sun.reflect.ClassFileAssembler::emitInt (46 bytes)
Event: 275.999 Thread 0x00007fc5400c9000 nmethod 536 0x00007fc530dfd7d0 code [0x00007fc530dfd9a0, 0x00007fc530dfded8]
Event: 275.999 Thread 0x00007fc5400c9000  538       3       java.lang.Class::arrayContentsEq (70 bytes)
Event: 276.000 Thread 0x00007fc5400c9000 nmethod 538 0x00007fc530dfd090 code [0x00007fc530dfd220, 0x00007fc530dfd670]
Event: 276.000 Thread 0x00007fc5400c9000  539       3       java.lang.Class::argumentTypesToString (79 bytes)
Event: 276.000 Thread 0x00007fc5400c9000 nmethod 539 0x00007fc530dfefd0 code [0x00007fc530dff200, 0x00007fc530dffe58]
Event: 276.000 Thread 0x00007fc5400c9000  537       3       java.util.AbstractCollection::<init> (5 bytes)
Event: 276.000 Thread 0x00007fc5400c9000 nmethod 537 0x00007fc530dfccd0 code [0x00007fc530dfce40, 0x00007fc530dfcff0]
Event: 276.000 Thread 0x00007fc5400c9000  541       3       java.lang.AbstractStringBuilder::append (40 bytes)

GC Heap History (4 events):
Event: 0.173 GC heap before
{Heap before GC invocations=1 (full 0):
 PSYoungGen      total 74752K, used 6451K [0x000000076cd80000, 0x0000000772080000, 0x00000007c0000000)
  eden space 64512K, 10% used [0x000000076cd80000,0x000000076d3ccde8,0x0000000770c80000)
  from space 10240K, 0% used [0x0000000771680000,0x0000000771680000,0x0000000772080000)
  to   space 10240K, 0% used [0x0000000770c80000,0x0000000770c80000,0x0000000771680000)
 ParOldGen       total 171008K, used 0K [0x00000006c6800000, 0x00000006d0f00000, 0x000000076cd80000)
  object space 171008K, 0% used [0x00000006c6800000,0x00000006c6800000,0x00000006d0f00000)
 Metaspace       used 5700K, capacity 5858K, committed 6144K, reserved 1056768K
  class space    used 665K, capacity 735K, committed 768K, reserved 1048576K
Event: 0.175 GC heap after
Heap after GC invocations=1 (full 0):
 PSYoungGen      total 74752K, used 944K [0x000000076cd80000, 0x0000000772080000, 0x00000007c0000000)
  eden space 64512K, 0% used [0x000000076cd80000,0x000000076cd80000,0x0000000770c80000)
  from space 10240K, 9% used [0x0000000770c80000,0x0000000770d6c010,0x0000000771680000)
  to   space 10240K, 0% used [0x0000000771680000,0x0000000771680000,0x0000000772080000)
 ParOldGen       total 171008K, used 8K [0x00000006c6800000, 0x00000006d0f00000, 0x000000076cd80000)
  object space 171008K, 0% used [0x00000006c6800000,0x00000006c6802000,0x00000006d0f00000)
 Metaspace       used 5700K, capacity 5858K, committed 6144K, reserved 1056768K
  class space    used 665K, capacity 735K, committed 768K, reserved 1048576K
}
Event: 0.175 GC heap before
{Heap before GC invocations=2 (full 1):
 PSYoungGen      total 74752K, used 944K [0x000000076cd80000, 0x0000000772080000, 0x00000007c0000000)
  eden space 64512K, 0% used [0x000000076cd80000,0x000000076cd80000,0x0000000770c80000)
  from space 10240K, 9% used [0x0000000770c80000,0x0000000770d6c010,0x0000000771680000)
  to   space 10240K, 0% used [0x0000000771680000,0x0000000771680000,0x0000000772080000)
 ParOldGen       total 171008K, used 8K [0x00000006c6800000, 0x00000006d0f00000, 0x000000076cd80000)
  object space 171008K, 0% used [0x00000006c6800000,0x00000006c6802000,0x00000006d0f00000)
 Metaspace       used 5700K, capacity 5858K, committed 6144K, reserved 1056768K
  class space    used 665K, capacity 735K, committed 768K, reserved 1048576K
Event: 0.182 GC heap after
Heap after GC invocations=2 (full 1):
 PSYoungGen      total 74752K, used 0K [0x000000076cd80000, 0x0000000772080000, 0x00000007c0000000)
  eden space 64512K, 0% used [0x000000076cd80000,0x000000076cd80000,0x0000000770c80000)
  from space 10240K, 0% used [0x0000000770c80000,0x0000000770c80000,0x0000000771680000)
  to   space 10240K, 0% used [0x0000000771680000,0x0000000771680000,0x0000000772080000)
 ParOldGen       total 171008K, used 882K [0x00000006c6800000, 0x00000006d0f00000, 0x000000076cd80000)
  object space 171008K, 0% used [0x00000006c6800000,0x00000006c68dc8d8,0x00000006d0f00000)
 Metaspace       used 5700K, capacity 5858K, committed 6144K, reserved 1056768K
  class space    used 665K, capacity 735K, committed 768K, reserved 1048576K
}

Deoptimization events (2 events):
Event: 0.153 Thread 0x00007fc54000a800 Uncommon trap: reason=null_check action=make_not_entrant pc=0x00007fc530d4ff68 method=java.lang.String.equals(Ljava/lang/Object;)Z @ 8
Event: 274.037 Thread 0x00007fc4cc001000 Uncommon trap: reason=unstable_if action=reinterpret pc=0x00007fc530d6dbb8 method=java.lang.String.replace(CC)Ljava/lang/String; @ 26

Internal exceptions (10 events):
Event: 274.354 Thread 0x00007fc4cc003000 Exception <a 'java/lang/ArrayIndexOutOfBoundsException'> (0x000000076d907b70) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/runtime/sharedRuntime.cpp, line 605]
Event: 274.355 Thread 0x00007fc4cc003000 Exception <a 'java/lang/ArrayIndexOutOfBoundsException'> (0x000000076d912868) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/runtime/sharedRuntime.cpp, line 605]
Event: 274.355 Thread 0x00007fc4cc003000 Exception <a 'java/lang/ArrayIndexOutOfBoundsException'> (0x000000076d919a40) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/runtime/sharedRuntime.cpp, line 605]
Event: 274.355 Thread 0x00007fc4cc003000 Exception <a 'java/lang/ArrayIndexOutOfBoundsException'> (0x000000076d91f1f8) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/runtime/sharedRuntime.cpp, line 605]
Event: 274.416 Thread 0x00007fc4cc003000 Exception <a 'java/lang/ArrayIndexOutOfBoundsException'> (0x000000076d935928) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/runtime/sharedRuntime.cpp, line 605]
Event: 274.442 Thread 0x00007fc4cc003000 Exception <a 'java/lang/NoSuchMethodError': <clinit>> (0x000000076d961d98) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/prims/jni.cpp, line 1613]
Event: 274.442 Thread 0x00007fc4cc003000 Exception <a 'java/lang/NoSuchMethodError': <clinit>> (0x000000076d96c8f0) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/prims/jni.cpp, line 1613]
Event: 275.997 Thread 0x00007fc4cc003000 Exception <a 'java/lang/NoSuchMethodError': <clinit>> (0x000000076d9b93e0) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/prims/jni.cpp, line 1613]
Event: 275.998 Thread 0x00007fc4cc003000 Exception <a 'java/lang/NoSuchMethodError': <clinit>> (0x000000076d9e89c8) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/prims/jni.cpp, line 1613]
Event: 276.000 Thread 0x00007fc4cc003000 Exception <a 'java/lang/NoSuchMethodError': <clinit>> (0x000000076da09e88) thrown at [/HUDSON3/workspace/8-2-build-linux-amd64/jdk8u121/8372/hotspot/src/share/vm/prims/jni.cpp, line 1613]

Events (10 events):
Event: 275.996 Executing VM operation: RevokeBias
Event: 275.996 Executing VM operation: RevokeBias done
Event: 275.996 loading class org/opencv/core/Point
Event: 275.996 loading class org/opencv/core/Point done
Event: 275.997 loading class org/opencv/core/Scalar
Event: 275.997 loading class org/opencv/core/Scalar done
Event: 275.997 loading class org/opencv/core/Rect
Event: 275.997 loading class org/opencv/core/Rect done
Event: 275.998 Executing VM operation: RevokeBias
Event: 275.998 Executing VM operation: RevokeBias done


Dynamic libraries:
00400000-00401000 r-xp 00000000 08:02 8260108                            /usr/lib/jvm/java-8-oracle/bin/java
00600000-00601000 rw-p 00000000 08:02 8260108                            /usr/lib/jvm/java-8-oracle/bin/java
022fb000-0231c000 rw-p 00000000 00:00 0                                  [heap]
6c6800000-6d0f00000 rw-p 00000000 00:00 0 
6d0f00000-76cd80000 ---p 00000000 00:00 0 
76cd80000-772080000 rw-p 00000000 00:00 0 
772080000-7c0000000 ---p 00000000 00:00 0 
7c0000000-7c0100000 rw-p 00000000 00:00 0 
7c0100000-800000000 ---p 00000000 00:00 0 
7fc494000000-7fc494021000 rw-p 00000000 00:00 0 
7fc494021000-7fc498000000 ---p 00000000 00:00 0 
7fc498000000-7fc4982a9000 rw-p 00000000 00:00 0 
7fc4982a9000-7fc49c000000 ---p 00000000 00:00 0 
7fc49c000000-7fc49c05b000 rw-p 00000000 00:00 0 
7fc49c05b000-7fc4a0000000 ---p 00000000 00:00 0 
7fc4a0000000-7fc4a0021000 rw-p 00000000 00:00 0 
7fc4a0021000-7fc4a4000000 ---p 00000000 00:00 0 
7fc4a4000000-7fc4a4021000 rw-p 00000000 00:00 0 
7fc4a4021000-7fc4a8000000 ---p 00000000 00:00 0 
7fc4a8000000-7fc4a8021000 rw-p 00000000 00:00 0 
7fc4a8021000-7fc4ac000000 ---p 00000000 00:00 0 
7fc4ac000000-7fc4ac021000 rw-p 00000000 00:00 0 
7fc4ac021000-7fc4b0000000 ---p 00000000 00:00 0 
7fc4b185c000-7fc4b185f000 ---p 00000000 00:00 0 
7fc4b185f000-7fc4b195d000 rwxp 00000000 00:00 0 
7fc4b195d000-7fc4b1960000 ---p 00000000 00:00 0 
7fc4b1960000-7fc4b1a5e000 rwxp 00000000 00:00 0 
7fc4b1a5e000-7fc4b1a61000 ---p 00000000 00:00 0 
7fc4b1a61000-7fc4b1b5f000 rwxp 00000000 00:00 0 
7fc4b1b5f000-7fc4b1b71000 r-xp 00000000 08:02 3277766                    /usr/lib/x86_64-linux-gnu/pkcs11/gnome-keyring-pkcs11.so
7fc4b1b71000-7fc4b1d70000 ---p 00012000 08:02 3277766                    /usr/lib/x86_64-linux-gnu/pkcs11/gnome-keyring-pkcs11.so
7fc4b1d70000-7fc4b1d72000 r--p 00011000 08:02 3277766                    /usr/lib/x86_64-linux-gnu/pkcs11/gnome-keyring-pkcs11.so
7fc4b1d72000-7fc4b1d73000 rw-p 00013000 08:02 3277766                    /usr/lib/x86_64-linux-gnu/pkcs11/gnome-keyring-pkcs11.so
7fc4b1d73000-7fc4b1da3000 r-xp 00000000 08:02 3278642                    /usr/lib/x86_64-linux-gnu/pkcs11/p11-kit-trust.so
7fc4b1da3000-7fc4b1fa3000 ---p 00030000 08:02 3278642                    /usr/lib/x86_64-linux-gnu/pkcs11/p11-kit-trust.so
7fc4b1fa3000-7fc4b1faa000 r--p 00030000 08:02 3278642                    /usr/lib/x86_64-linux-gnu/pkcs11/p11-kit-trust.so
7fc4b1faa000-7fc4b1fab000 rw-p 00037000 08:02 3278642                    /usr/lib/x86_64-linux-gnu/pkcs11/p11-kit-trust.so
7fc4b1fab000-7fc4b1fac000 ---p 00000000 00:00 0 
7fc4b1fac000-7fc4b27ac000 rwxp 00000000 00:00 0 
7fc4b27ac000-7fc4b27f0000 r-xp 00000000 08:02 24379420                   /lib/x86_64-linux-gnu/libdbus-1.so.3.7.6
7fc4b27f0000-7fc4b29ef000 ---p 00044000 08:02 24379420                   /lib/x86_64-linux-gnu/libdbus-1.so.3.7.6
7fc4b29ef000-7fc4b29f0000 r--p 00043000 08:02 24379420                   /lib/x86_64-linux-gnu/libdbus-1.so.3.7.6
7fc4b29f0000-7fc4b29f1000 rw-p 00044000 08:02 24379420                   /lib/x86_64-linux-gnu/libdbus-1.so.3.7.6
7fc4b29f1000-7fc4b29f9000 r-xp 00000000 08:02 24383625                   /lib/x86_64-linux-gnu/libnih-dbus.so.1.0.0
7fc4b29f9000-7fc4b2bf9000 ---p 00008000 08:02 24383625                   /lib/x86_64-linux-gnu/libnih-dbus.so.1.0.0
7fc4b2bf9000-7fc4b2bfa000 r--p 00008000 08:02 24383625                   /lib/x86_64-linux-gnu/libnih-dbus.so.1.0.0
7fc4b2bfa000-7fc4b2bfb000 rw-p 00009000 08:02 24383625                   /lib/x86_64-linux-gnu/libnih-dbus.so.1.0.0
7fc4b2bfb000-7fc4b2c12000 r-xp 00000000 08:02 24383627                   /lib/x86_64-linux-gnu/libnih.so.1.0.0
7fc4b2c12000-7fc4b2e11000 ---p 00017000 08:02 24383627                   /lib/x86_64-linux-gnu/libnih.so.1.0.0
7fc4b2e11000-7fc4b2e12000 r--p 00016000 08:02 24383627                   /lib/x86_64-linux-gnu/libnih.so.1.0.0
7fc4b2e12000-7fc4b2e13000 rw-p 00017000 08:02 24383627                   /lib/x86_64-linux-gnu/libnih.so.1.0.0
7fc4b2e13000-7fc4b2e2c000 r-xp 00000000 08:02 24380487                   /lib/x86_64-linux-gnu/libcgmanager.so.0.0.0
7fc4b2e2c000-7fc4b302b000 ---p 00019000 08:02 24380487                   /lib/x86_64-linux-gnu/libcgmanager.so.0.0.0
7fc4b302b000-7fc4b302d000 r--p 00018000 08:02 24380487                   /lib/x86_64-linux-gnu/libcgmanager.so.0.0.0
7fc4b302d000-7fc4b302e000 rw-p 0001a000 08:02 24380487                   /lib/x86_64-linux-gnu/libcgmanager.so.0.0.0
7fc4b302e000-7fc4b3034000 r-xp 00000000 08:02 2891413                    /usr/lib/x86_64-linux-gnu/libdatrie.so.1.3.1
7fc4b3034000-7fc4b3233000 ---p 00006000 08:02 2891413                    /usr/lib/x86_64-linux-gnu/libdatrie.so.1.3.1
7fc4b3233000-7fc4b3234000 r--p 00005000 08:02 2891413                    /usr/lib/x86_64-linux-gnu/libdatrie.so.1.3.1
7fc4b3234000-7fc4b3235000 rw-p 00006000 08:02 2891413                    /usr/lib/x86_64-linux-gnu/libdatrie.so.1.3.1
7fc4b3235000-7fc4b3259000 r-xp 00000000 08:02 2887436                    /usr/lib/x86_64-linux-gnu/libgraphite2.so.3.0.1
7fc4b3259000-7fc4b3458000 ---p 00024000 08:02 2887436                    /usr/lib/x86_64-linux-gnu/libgraphite2.so.3.0.1
7fc4b3458000-7fc4b345a000 r--p 00023000 08:02 2887436                    /usr/lib/x86_64-linux-gnu/libgraphite2.so.3.0.1
7fc4b345a000-7fc4b345b000 rw-p 00025000 08:02 2887436                    /usr/lib/x86_64-linux-gnu/libgraphite2.so.3.0.1
7fc4b345b000-7fc4b345f000 r-xp 00000000 08:02 24383596                   /lib/x86_64-linux-gnu/libgpg-error.so.0.10.0
7fc4b345f000-7fc4b365e000 ---p 00004000 08:02 24383596                   /lib/x86_64-linux-gnu/libgpg-error.so.0.10.0
7fc4b365e000-7fc4b365f000 r--p 00003000 08:02 24383596                   /lib/x86_64-linux-gnu/libgpg-error.so.0.10.0
7fc4b365f000-7fc4b3660000 rw-p 00004000 08:02 24383596                   /lib/x86_64-linux-gnu/libgpg-error.so.0.10.0
7fc4b3660000-7fc4b3670000 r-xp 00000000 08:02 24380422                   /lib/x86_64-linux-gnu/libudev.so.1.3.5
7fc4b3670000-7fc4b386f000 ---p 00010000 08:02 24380422                   /lib/x86_64-linux-gnu/libudev.so.1.3.5
7fc4b386f000-7fc4b3870000 r--p 0000f000 08:02 24380422                   /lib/x86_64-linux-gnu/libudev.so.1.3.5
7fc4b3870000-7fc4b3871000 rw-p 00010000 08:02 24380422                   /lib/x86_64-linux-gnu/libudev.so.1.3.5
7fc4b3871000-7fc4b3876000 r-xp 00000000 08:02 2891221                    /usr/lib/x86_64-linux-gnu/libXdmcp.so.6.0.0
7fc4b3876000-7fc4b3a75000 ---p 00005000 08:02 2891221                    /usr/lib/x86_64-linux-gnu/libXdmcp.so.6.0.0
7fc4b3a75000-7fc4b3a76000 r--p 00004000 08:02 2891221                    /usr/lib/x86_64-linux-gnu/libXdmcp.so.6.0.0
7fc4b3a76000-7fc4b3a77000 rw-p 00005000 08:02 2891221                    /usr/lib/x86_64-linux-gnu/libXdmcp.so.6.0.0
7fc4b3a77000-7fc4b3a79000 r-xp 00000000 08:02 2891210                    /usr/lib/x86_64-linux-gnu/libXau.so.6.0.0
7fc4b3a79000-7fc4b3c79000 ---p 00002000 08:02 2891210                    /usr/lib/x86_64-linux-gnu/libXau.so.6.0.0
7fc4b3c79000-7fc4b3c7a000 r--p 00002000 08:02 2891210                    /usr/lib/x86_64-linux-gnu/libXau.so.6.0.0
7fc4b3c7a000-7fc4b3c7b000 rw-p 00003000 08:02 2891210                    /usr/lib/x86_64-linux-gnu/libXau.so.6.0.0
7fc4b3c7b000-7fc4b3ca2000 r-xp 00000000 08:02 24383637                   /lib/x86_64-linux-gnu/libexpat.so.1.6.0
7fc4b3ca2000-7fc4b3ea2000 ---p 00027000 08:02 24383637                   /lib/x86_64-linux-gnu/libexpat.so.1.6.0
7fc4b3ea2000-7fc4b3ea4000 r--p 00027000 08:02 24383637                   /lib/x86_64-linux-gnu/libexpat.so.1.6.0
7fc4b3ea4000-7fc4b3ea5000 rw-p 00029000 08:02 24383637                   /lib/x86_64-linux-gnu/libexpat.so.1.6.0
7fc4b3ea5000-7fc4b3ead000 r-xp 00000000 08:02 2892136                    /usr/lib/x86_64-linux-gnu/libthai.so.0.2.0
7fc4b3ead000-7fc4b40ac000 ---p 00008000 08:02 2892136                    /usr/lib/x86_64-linux-gnu/libthai.so.0.2.0
7fc4b40ac000-7fc4b40ad000 r--p 00007000 08:02 2892136                    /usr/lib/x86_64-linux-gnu/libthai.so.0.2.0
7fc4b40ad000-7fc4b40ae000 rw-p 00008000 08:02 2892136                    /usr/lib/x86_64-linux-gnu/libthai.so.0.2.0
7fc4b40ae000-7fc4b4101000 r-xp 00000000 08:02 2887455                    /usr/lib/x86_64-linux-gnu/libharfbuzz.so.0.927.0
7fc4b4101000-7fc4b4301000 ---p 00053000 08:02 2887455                    /usr/lib/x86_64-linux-gnu/libharfbuzz.so.0.927.0
7fc4b4301000-7fc4b4302000 r--p 00053000 08:02 2887455                    /usr/lib/x86_64-linux-gnu/libharfbuzz.so.0.927.0
7fc4b4302000-7fc4b4303000 rw-p 00054000 08:02 2887455                    /usr/lib/x86_64-linux-gnu/libharfbuzz.so.0.927.0
7fc4b4303000-7fc4b4323000 r-xp 00000000 08:02 24383693                   /lib/x86_64-linux-gnu/libselinux.so.1
7fc4b4323000-7fc4b4522000 ---p 00020000 08:02 24383693                   /lib/x86_64-linux-gnu/libselinux.so.1
7fc4b4522000-7fc4b4523000 r--p 0001f000 08:02 24383693                   /lib/x86_64-linux-gnu/libselinux.so.1
7fc4b4523000-7fc4b4524000 rw-p 00020000 08:02 24383693                   /lib/x86_64-linux-gnu/libselinux.so.1
7fc4b4524000-7fc4b4526000 rw-p 00000000 00:00 0 
7fc4b4526000-7fc4b4561000 r-xp 00000000 08:02 2891937                    /usr/lib/x86_64-linux-gnu/libp11-kit.so.0.0.0
7fc4b4561000-7fc4b4760000 ---p 0003b000 08:02 2891937                    /usr/lib/x86_64-linux-gnu/libp11-kit.so.0.0.0
7fc4b4760000-7fc4b4766000 r--p 0003a000 08:02 2891937                    /usr/lib/x86_64-linux-gnu/libp11-kit.so.0.0.0
7fc4b4766000-7fc4b4768000 rw-p 00040000 08:02 2891937                    /usr/lib/x86_64-linux-gnu/libp11-kit.so.0.0.0
7fc4b4768000-7fc4b477a000 r-xp 00000000 08:02 2885876                    /usr/lib/x86_64-linux-gnu/libtasn1.so.6.2.0
7fc4b477a000-7fc4b497a000 ---p 00012000 08:02 2885876                    /usr/lib/x86_64-linux-gnu/libtasn1.so.6.2.0
7fc4b497a000-7fc4b497b000 r--p 00012000 08:02 2885876                    /usr/lib/x86_64-linux-gnu/libtasn1.so.6.2.0
7fc4b497b000-7fc4b497c000 rw-p 00013000 08:02 2885876                    /usr/lib/x86_64-linux-gnu/libtasn1.so.6.2.0
7fc4b497c000-7fc4b49f8000 r-xp 00000000 08:02 24383584                   /lib/x86_64-linux-gnu/libgcrypt.so.11.8.2
7fc4b49f8000-7fc4b4bf8000 ---p 0007c000 08:02 24383584                   /lib/x86_64-linux-gnu/libgcrypt.so.11.8.2
7fc4b4bf8000-7fc4b4bf9000 r--p 0007c000 08:02 24383584                   /lib/x86_64-linux-gnu/libgcrypt.so.11.8.2
7fc4b4bf9000-7fc4b4bfc000 rw-p 0007d000 08:02 24383584                   /lib/x86_64-linux-gnu/libgcrypt.so.11.8.2
7fc4b4bfc000-7fc4b4c79000 r-xp 00000000 08:02 2891933                    /usr/lib/x86_64-linux-gnu/liborc-0.4.so.0.18.0
7fc4b4c79000-7fc4b4e78000 ---p 0007d000 08:02 2891933                    /usr/lib/x86_64-linux-gnu/liborc-0.4.so.0.18.0
7fc4b4e78000-7fc4b4e7a000 r--p 0007c000 08:02 2891933                    /usr/lib/x86_64-linux-gnu/liborc-0.4.so.0.18.0
7fc4b4e7a000-7fc4b4e7e000 rw-p 0007e000 08:02 2891933                    /usr/lib/x86_64-linux-gnu/liborc-0.4.so.0.18.0
7fc4b4e7e000-7fc4b4e85000 r-xp 00000000 08:02 2891929                    /usr/lib/x86_64-linux-gnu/libogg.so.0.8.1
7fc4b4e85000-7fc4b5085000 ---p 00007000 08:02 2891929                    /usr/lib/x86_64-linux-gnu/libogg.so.0.8.1
7fc4b5085000-7fc4b5086000 r--p 00007000 08:02 2891929                    /usr/lib/x86_64-linux-gnu/libogg.so.0.8.1
7fc4b5086000-7fc4b5087000 rw-p 00008000 08:02 2891929                    /usr/lib/x86_64-linux-gnu/libogg.so.0.8.1
7fc4b5087000-7fc4b509c000 r-xp 00000000 08:02 24383717                   /lib/x86_64-linux-gnu/libusb-1.0.so.0.1.0
7fc4b509c000-7fc4b529c000 ---p 00015000 08:02 24383717                   /lib/x86_64-linux-gnu/libusb-1.0.so.0.1.0
7fc4b529c000-7fc4b529d000 r--p 00015000 08:02 24383717                   /lib/x86_64-linux-gnu/libusb-1.0.so.0.1.0
7fc4b529d000-7fc4b529e000 rw-p 00016000 08:02 24383717                   /lib/x86_64-linux-gnu/libusb-1.0.so.0.1.0
7fc4b529e000-7fc4b52ab000 r-xp 00000000 08:02 2892024                    /usr/lib/x86_64-linux-gnu/libraw1394.so.11.1.0
7fc4b52ab000-7fc4b54aa000 ---p 0000d000 08:02 2892024                    /usr/lib/x86_64-linux-gnu/libraw1394.so.11.1.0
7fc4b54aa000-7fc4b54ab000 r--p 0000c000 08:02 2892024                    /usr/lib/x86_64-linux-gnu/libraw1394.so.11.1.0
7fc4b54ab000-7fc4b54ac000 rw-p 0000d000 08:02 2892024                    /usr/lib/x86_64-linux-gnu/libraw1394.so.11.1.0
7fc4b54ac000-7fc4b54e9000 r-xp 00000000 08:02 24383616                   /lib/x86_64-linux-gnu/libpcre.so.3.13.1
7fc4b54e9000-7fc4b56e8000 ---p 0003d000 08:02 24383616                   /lib/x86_64-linux-gnu/libpcre.so.3.13.1
7fc4b56e8000-7fc4b56e9000 r--p 0003c000 08:02 24383616                   /lib/x86_64-linux-gnu/libpcre.so.3.13.1
7fc4b56e9000-7fc4b56ea000 rw-p 0003d000 08:02 24383616                   /lib/x86_64-linux-gnu/libpcre.so.3.13.1
7fc4b56ea000-7fc4b56f1000 r-xp 00000000 08:02 2885873                    /usr/lib/x86_64-linux-gnu/libffi.so.6.0.1
7fc4b56f1000-7fc4b58f0000 ---p 00007000 08:02 2885873                    /usr/lib/x86_64-linux-gnu/libffi.so.6.0.1
7fc4b58f0000-7fc4b58f1000 r--p 00006000 08:02 2885873                    /usr/lib/x86_64-linux-gnu/libffi.so.6.0.1
7fc4b58f1000-7fc4b58f2000 rw-p 00007000 08:02 2885873                    /usr/lib/x86_64-linux-gnu/libffi.so.6.0.1
7fc4b58f2000-7fc4b590f000 r-xp 00000000 08:02 2892276                    /usr/lib/x86_64-linux-gnu/libxcb.so.1.1.0
7fc4b590f000-7fc4b5b0f000 ---p 0001d000 08:02 2892276                    /usr/lib/x86_64-linux-gnu/libxcb.so.1.1.0
7fc4b5b0f000-7fc4b5b10000 r--p 0001d000 08:02 2892276                    /usr/lib/x86_64-linux-gnu/libxcb.so.1.1.0
7fc4b5b10000-7fc4b5b11000 rw-p 0001e000 08:02 2892276                    /usr/lib/x86_64-linux-gnu/libxcb.so.1.1.0
7fc4b5b11000-7fc4b5b19000 r-xp 00000000 08:02 2892262                    /usr/lib/x86_64-linux-gnu/libxcb-render.so.0.0.0
7fc4b5b19000-7fc4b5d18000 ---p 00008000 08:02 2892262                    /usr/lib/x86_64-linux-gnu/libxcb-render.so.0.0.0
7fc4b5d18000-7fc4b5d19000 r--p 00007000 08:02 2892262                    /usr/lib/x86_64-linux-gnu/libxcb-render.so.0.0.0
7fc4b5d19000-7fc4b5d1a000 rw-p 00008000 08:02 2892262                    /usr/lib/x86_64-linux-gnu/libxcb-render.so.0.0.0
7fc4b5d1a000-7fc4b5d1c000 r-xp 00000000 08:02 2892266                    /usr/lib/x86_64-linux-gnu/libxcb-shm.so.0.0.0
7fc4b5d1c000-7fc4b5f1b000 ---p 00002000 08:02 2892266                    /usr/lib/x86_64-linux-gnu/libxcb-shm.so.0.0.0
7fc4b5f1b000-7fc4b5f1c000 r--p 00001000 08:02 2892266                    /usr/lib/x86_64-linux-gnu/libxcb-shm.so.0.0.0
7fc4b5f1c000-7fc4b5f1d000 rw-p 00002000 08:02 2892266                    /usr/lib/x86_64-linux-gnu/libxcb-shm.so.0.0.0
7fc4b5f1d000-7fc4b5fba000 r-xp 00000000 08:02 2887353                    /usr/lib/x86_64-linux-gnu/libfreetype.so.6.11.1
7fc4b5fba000-7fc4b61b9000 ---p 0009d000 08:02 2887353                    /usr/lib/x86_64-linux-gnu/libfreetype.so.6.11.1
7fc4b61b9000-7fc4b61bf000 r--p 0009c000 08:02 2887353                    /usr/lib/x86_64-linux-gnu/libfreetype.so.6.11.1
7fc4b61bf000-7fc4b61c0000 rw-p 000a2000 08:02 2887353                    /usr/lib/x86_64-linux-gnu/libfreetype.so.6.11.1
7fc4b61c0000-7fc4b6260000 r-xp 00000000 08:02 2887415                    /usr/lib/x86_64-linux-gnu/libpixman-1.so.0.30.2
7fc4b6260000-7fc4b6460000 ---p 000a0000 08:02 2887415                    /usr/lib/x86_64-linux-gnu/libpixman-1.so.0.30.2
7fc4b6460000-7fc4b6467000 r--p 000a0000 08:02 2887415                    /usr/lib/x86_64-linux-gnu/libpixman-1.so.0.30.2
7fc4b6467000-7fc4b6468000 rw-p 000a7000 08:02 2887415                    /usr/lib/x86_64-linux-gnu/libpixman-1.so.0.30.2
7fc4b6468000-7fc4b6479000 r-xp 00000000 08:02 2891223                    /usr/lib/x86_64-linux-gnu/libXext.so.6.4.0
7fc4b6479000-7fc4b6678000 ---p 00011000 08:02 2891223                    /usr/lib/x86_64-linux-gnu/libXext.so.6.4.0
7fc4b6678000-7fc4b6679000 r--p 00010000 08:02 2891223                    /usr/lib/x86_64-linux-gnu/libXext.so.6.4.0
7fc4b6679000-7fc4b667a000 rw-p 00011000 08:02 2891223                    /usr/lib/x86_64-linux-gnu/libXext.so.6.4.0
7fc4b667a000-7fc4b667c000 r-xp 00000000 08:02 2891219                    /usr/lib/x86_64-linux-gnu/libXdamage.so.1.1.0
7fc4b667c000-7fc4b687b000 ---p 00002000 08:02 2891219                    /usr/lib/x86_64-linux-gnu/libXdamage.so.1.1.0
7fc4b687b000-7fc4b687c000 r--p 00001000 08:02 2891219                    /usr/lib/x86_64-linux-gnu/libXdamage.so.1.1.0
7fc4b687c000-7fc4b687d000 rw-p 00002000 08:02 2891219                    /usr/lib/x86_64-linux-gnu/libXdamage.so.1.1.0
7fc4b687d000-7fc4b687f000 r-xp 00000000 08:02 2891215                    /usr/lib/x86_64-linux-gnu/libXcomposite.so.1.0.0
7fc4b687f000-7fc4b6a7e000 ---p 00002000 08:02 2891215                    /usr/lib/x86_64-linux-gnu/libXcomposite.so.1.0.0
7fc4b6a7e000-7fc4b6a7f000 r--p 00001000 08:02 2891215                    /usr/lib/x86_64-linux-gnu/libXcomposite.so.1.0.0
7fc4b6a7f000-7fc4b6a80000 rw-p 00002000 08:02 2891215                    /usr/lib/x86_64-linux-gnu/libXcomposite.so.1.0.0
7fc4b6a80000-7fc4b6a89000 r-xp 00000000 08:02 2891217                    /usr/lib/x86_64-linux-gnu/libXcursor.so.1.0.2
7fc4b6a89000-7fc4b6c88000 ---p 00009000 08:02 2891217                    /usr/lib/x86_64-linux-gnu/libXcursor.so.1.0.2
7fc4b6c88000-7fc4b6c89000 r--p 00008000 08:02 2891217                    /usr/lib/x86_64-linux-gnu/libXcursor.so.1.0.2
7fc4b6c89000-7fc4b6c8a000 rw-p 00009000 08:02 2891217                    /usr/lib/x86_64-linux-gnu/libXcursor.so.1.0.2
7fc4b6c8a000-7fc4b6c93000 r-xp 00000000 08:02 2884066                    /usr/lib/x86_64-linux-gnu/libXrandr.so.2.2.0
7fc4b6c93000-7fc4b6e92000 ---p 00009000 08:02 2884066                    /usr/lib/x86_64-linux-gnu/libXrandr.so.2.2.0
7fc4b6e92000-7fc4b6e93000 r--p 00008000 08:02 2884066                    /usr/lib/x86_64-linux-gnu/libXrandr.so.2.2.0
7fc4b6e93000-7fc4b6e94000 rw-p 00009000 08:02 2884066                    /usr/lib/x86_64-linux-gnu/libXrandr.so.2.2.0
7fc4b6e94000-7fc4b6ea3000 r-xp 00000000 08:02 2891231                    /usr/lib/x86_64-linux-gnu/libXi.so.6.1.0
7fc4b6ea3000-7fc4b70a2000 ---p 0000f000 08:02 2891231                    /usr/lib/x86_64-linux-gnu/libXi.so.6.1.0
7fc4b70a2000-7fc4b70a3000 r--p 0000e000 08:02 2891231                    /usr/lib/x86_64-linux-gnu/libXi.so.6.1.0
7fc4b70a3000-7fc4b70a4000 rw-p 0000f000 08:02 2891231                    /usr/lib/x86_64-linux-gnu/libXi.so.6.1.0
7fc4b70a4000-7fc4b70a6000 r-xp 00000000 08:02 2891233                    /usr/lib/x86_64-linux-gnu/libXinerama.so.1.0.0
7fc4b70a6000-7fc4b72a5000 ---p 00002000 08:02 2891233                    /usr/lib/x86_64-linux-gnu/libXinerama.so.1.0.0
7fc4b72a5000-7fc4b72a6000 r--p 00001000 08:02 2891233                    /usr/lib/x86_64-linux-gnu/libXinerama.so.1.0.0
7fc4b72a6000-7fc4b72a7000 rw-p 00002000 08:02 2891233                    /usr/lib/x86_64-linux-gnu/libXinerama.so.1.0.0
7fc4b72a7000-7fc4b72b0000 r-xp 00000000 08:02 2891245                    /usr/lib/x86_64-linux-gnu/libXrender.so.1.3.0
7fc4b72b0000-7fc4b74af000 ---p 00009000 08:02 2891245                    /usr/lib/x86_64-linux-gnu/libXrender.so.1.3.0
7fc4b74af000-7fc4b74b0000 r--p 00008000 08:02 2891245                    /usr/lib/x86_64-linux-gnu/libXrender.so.1.3.0
7fc4b74b0000-7fc4b74b1000 rw-p 00009000 08:02 2891245                    /usr/lib/x86_64-linux-gnu/libXrender.so.1.3.0
7fc4b74b1000-7fc4b74eb000 r-xp 00000000 08:02 2887351                    /usr/lib/x86_64-linux-gnu/libfontconfig.so.1.8.0
7fc4b74eb000-7fc4b76ea000 ---p 0003a000 08:02 2887351                    /usr/lib/x86_64-linux-gnu/libfontconfig.so.1.8.0
7fc4b76ea000-7fc4b76ec000 r--p 00039000 08:02 2887351                    /usr/lib/x86_64-linux-gnu/libfontconfig.so.1.8.0
7fc4b76ec000-7fc4b76ed000 rw-p 0003b000 08:02 2887351                    /usr/lib/x86_64-linux-gnu/libfontconfig.so.1.8.0
7fc4b76ed000-7fc4b7737000 r-xp 00000000 08:02 2891945                    /usr/lib/x86_64-linux-gnu/libpango-1.0.so.0.3600.3
7fc4b7737000-7fc4b7937000 ---p 0004a000 08:02 2891945                    /usr/lib/x86_64-linux-gnu/libpango-1.0.so.0.3600.3
7fc4b7937000-7fc4b7939000 r--p 0004a000 08:02 2891945                    /usr/lib/x86_64-linux-gnu/libpango-1.0.so.0.3600.3
7fc4b7939000-7fc4b793a000 rw-p 0004c000 08:02 2891945                    /usr/lib/x86_64-linux-gnu/libpango-1.0.so.0.3600.3
7fc4b793a000-7fc4b794e000 r-xp 00000000 08:02 2891949                    /usr/lib/x86_64-linux-gnu/libpangoft2-1.0.so.0.3600.3
7fc4b794e000-7fc4b7b4d000 ---p 00014000 08:02 2891949                    /usr/lib/x86_64-linux-gnu/libpangoft2-1.0.so.0.3600.3
7fc4b7b4d000-7fc4b7b4e000 r--p 00013000 08:02 2891949                    /usr/lib/x86_64-linux-gnu/libpangoft2-1.0.so.0.3600.3
7fc4b7b4e000-7fc4b7b4f000 rw-p 00014000 08:02 2891949                    /usr/lib/x86_64-linux-gnu/libpangoft2-1.0.so.0.3600.3
7fc4b7b4f000-7fc4b7cbb000 r-xp 00000000 08:02 2891593                    /usr/lib/x86_64-linux-gnu/libgio-2.0.so.0.4002.0
7fc4b7cbb000-7fc4b7eba000 ---p 0016c000 08:02 2891593                    /usr/lib/x86_64-linux-gnu/libgio-2.0.so.0.4002.0
7fc4b7eba000-7fc4b7ebe000 r--p 0016b000 08:02 2891593                    /usr/lib/x86_64-linux-gnu/libgio-2.0.so.0.4002.0
7fc4b7ebe000-7fc4b7ec0000 rw-p 0016f000 08:02 2891593                    /usr/lib/x86_64-linux-gnu/libgio-2.0.so.0.4002.0
7fc4b7ec0000-7fc4b7ec2000 rw-p 00000000 00:00 0 
7fc4b7ec2000-7fc4b7ee1000 r-xp 00000000 08:02 2891302                    /usr/lib/x86_64-linux-gnu/libatk-1.0.so.0.21009.1
7fc4b7ee1000-7fc4b80e1000 ---p 0001f000 08:02 2891302                    /usr/lib/x86_64-linux-gnu/libatk-1.0.so.0.21009.1
7fc4b80e1000-7fc4b80e3000 r--p 0001f000 08:02 2891302                    /usr/lib/x86_64-linux-gnu/libatk-1.0.so.0.21009.1
7fc4b80e3000-7fc4b80e4000 rw-p 00021000 08:02 2891302                    /usr/lib/x86_64-linux-gnu/libatk-1.0.so.0.21009.1
7fc4b80e4000-7fc4b80e9000 r-xp 00000000 08:02 2891225                    /usr/lib/x86_64-linux-gnu/libXfixes.so.3.1.0
7fc4b80e9000-7fc4b82e8000 ---p 00005000 08:02 2891225                    /usr/lib/x86_64-linux-gnu/libXfixes.so.3.1.0
7fc4b82e8000-7fc4b82e9000 r--p 00004000 08:02 2891225                    /usr/lib/x86_64-linux-gnu/libXfixes.so.3.1.0
7fc4b82e9000-7fc4b82ea000 rw-p 00005000 08:02 2891225                    /usr/lib/x86_64-linux-gnu/libXfixes.so.3.1.0
7fc4b82ea000-7fc4b841a000 r-xp 00000000 08:02 2891206                    /usr/lib/x86_64-linux-gnu/libX11.so.6.3.0
7fc4b841a000-7fc4b861a000 ---p 00130000 08:02 2891206                    /usr/lib/x86_64-linux-gnu/libX11.so.6.3.0
7fc4b861a000-7fc4b861b000 r--p 00130000 08:02 2891206                    /usr/lib/x86_64-linux-gnu/libX11.so.6.3.0
7fc4b861b000-7fc4b861f000 rw-p 00131000 08:02 2891206                    /usr/lib/x86_64-linux-gnu/libX11.so.6.3.0
7fc4b861f000-7fc4b862a000 r-xp 00000000 08:02 2891947                    /usr/lib/x86_64-linux-gnu/libpangocairo-1.0.so.0.3600.3
7fc4b862a000-7fc4b882a000 ---p 0000b000 08:02 2891947                    /usr/lib/x86_64-linux-gnu/libpangocairo-1.0.so.0.3600.3
7fc4b882a000-7fc4b882b000 r--p 0000b000 08:02 2891947                    /usr/lib/x86_64-linux-gnu/libpangocairo-1.0.so.0.3600.3
7fc4b882b000-7fc4b882c000 rw-p 0000c000 08:02 2891947                    /usr/lib/x86_64-linux-gnu/libpangocairo-1.0.so.0.3600.3
7fc4b882c000-7fc4b882f000 r-xp 00000000 08:02 2891605                    /usr/lib/x86_64-linux-gnu/libgmodule-2.0.so.0.4002.0
7fc4b882f000-7fc4b8a2e000 ---p 00003000 08:02 2891605                    /usr/lib/x86_64-linux-gnu/libgmodule-2.0.so.0.4002.0
7fc4b8a2e000-7fc4b8a2f000 r--p 00002000 08:02 2891605                    /usr/lib/x86_64-linux-gnu/libgmodule-2.0.so.0.4002.0
7fc4b8a2f000-7fc4b8a30000 rw-p 00003000 08:02 2891605                    /usr/lib/x86_64-linux-gnu/libgmodule-2.0.so.0.4002.0
7fc4b8a30000-7fc4b8a3f000 r-xp 00000000 08:02 24383562                   /lib/x86_64-linux-gnu/libbz2.so.1.0.4
7fc4b8a3f000-7fc4b8c3e000 ---p 0000f000 08:02 24383562                   /lib/x86_64-linux-gnu/libbz2.so.1.0.4
7fc4b8c3e000-7fc4b8c3f000 r--p 0000e000 08:02 24383562                   /lib/x86_64-linux-gnu/libbz2.so.1.0.4
7fc4b8c3f000-7fc4b8c40000 rw-p 0000f000 08:02 24383562                   /lib/x86_64-linux-gnu/libbz2.so.1.0.4
7fc4b8c40000-7fc4b8cf7000 r-xp 00000000 08:02 2885880                    /usr/lib/x86_64-linux-gnu/libgnutls.so.26.22.6
7fc4b8cf7000-7fc4b8ef6000 ---p 000b7000 08:02 2885880                    /usr/lib/x86_64-linux-gnu/libgnutls.so.26.22.6
7fc4b8ef6000-7fc4b8efc000 r--p 000b6000 08:02 2885880                    /usr/lib/x86_64-linux-gnu/libgnutls.so.26.22.6
7fc4b8efc000-7fc4b8efd000 rw-p 000bc000 08:02 2885880                    /usr/lib/x86_64-linux-gnu/libgnutls.so.26.22.6
7fc4b8efd000-7fc4b8efe000 rw-p 00000000 00:00 0 
7fc4b8efe000-7fc4b8f17000 r-xp 00000000 08:02 2892046                    /usr/lib/x86_64-linux-gnu/librtmp.so.0
7fc4b8f17000-7fc4b9116000 ---p 00019000 08:02 2892046                    /usr/lib/x86_64-linux-gnu/librtmp.so.0
7fc4b9116000-7fc4b9117000 r--p 00018000 08:02 2892046                    /usr/lib/x86_64-linux-gnu/librtmp.so.0
7fc4b9117000-7fc4b9118000 rw-p 00019000 08:02 2892046                    /usr/lib/x86_64-linux-gnu/librtmp.so.0
7fc4b9118000-7fc4b912c000 r-xp 00000000 08:02 2893565                    /usr/lib/x86_64-linux-gnu/libva.so.1.3500.0
7fc4b912c000-7fc4b932c000 ---p 00014000 08:02 2893565                    /usr/lib/x86_64-linux-gnu/libva.so.1.3500.0
7fc4b932c000-7fc4b932d000 r--p 00014000 08:02 2893565                    /usr/lib/x86_64-linux-gnu/libva.so.1.3500.0
7fc4b932d000-7fc4b932e000 rw-p 00015000 08:02 2893565                    /usr/lib/x86_64-linux-gnu/libva.so.1.3500.0
7fc4b932e000-7fc4b933b000 r-xp 00000000 08:02 2893555                    /usr/lib/x86_64-linux-gnu/libgsm.so.1.0.12
7fc4b933b000-7fc4b953a000 ---p 0000d000 08:02 2893555                    /usr/lib/x86_64-linux-gnu/libgsm.so.1.0.12
7fc4b953a000-7fc4b953b000 r--p 0000c000 08:02 2893555                    /usr/lib/x86_64-linux-gnu/libgsm.so.1.0.12
7fc4b953b000-7fc4b953c000 rw-p 0000d000 08:02 2893555                    /usr/lib/x86_64-linux-gnu/libgsm.so.1.0.12
7fc4b953c000-7fc4b9599000 r-xp 00000000 08:02 2893557                    /usr/lib/x86_64-linux-gnu/libmp3lame.so.0.0.0
7fc4b9599000-7fc4b9799000 ---p 0005d000 08:02 2893557                    /usr/lib/x86_64-linux-gnu/libmp3lame.so.0.0.0
7fc4b9799000-7fc4b979a000 r--p 0005d000 08:02 2893557                    /usr/lib/x86_64-linux-gnu/libmp3lame.so.0.0.0
7fc4b979a000-7fc4b979b000 rw-p 0005e000 08:02 2893557                    /usr/lib/x86_64-linux-gnu/libmp3lame.so.0.0.0
7fc4b979b000-7fc4b97c9000 rw-p 00000000 00:00 0 
7fc4b97c9000-7fc4b97ea000 r-xp 00000000 08:02 2893559                    /usr/lib/x86_64-linux-gnu/libopenjpeg-2.1.3.0.so
7fc4b97ea000-7fc4b99e9000 ---p 00021000 08:02 2893559                    /usr/lib/x86_64-linux-gnu/libopenjpeg-2.1.3.0.so
7fc4b99e9000-7fc4b99ea000 r--p 00020000 08:02 2893559                    /usr/lib/x86_64-linux-gnu/libopenjpeg-2.1.3.0.so
7fc4b99ea000-7fc4b99eb000 rw-p 00021000 08:02 2893559                    /usr/lib/x86_64-linux-gnu/libopenjpeg-2.1.3.0.so
7fc4b99eb000-7fc4b9a32000 r-xp 00000000 08:02 2893561                    /usr/lib/x86_64-linux-gnu/libopus.so.0.5.0
7fc4b9a32000-7fc4b9c31000 ---p 00047000 08:02 2893561                    /usr/lib/x86_64-linux-gnu/libopus.so.0.5.0
7fc4b9c31000-7fc4b9c32000 r--p 00046000 08:02 2893561                    /usr/lib/x86_64-linux-gnu/libopus.so.0.5.0
7fc4b9c32000-7fc4b9c33000 rw-p 00047000 08:02 2893561                    /usr/lib/x86_64-linux-gnu/libopus.so.0.5.0
7fc4b9c33000-7fc4b9cf3000 r-xp 00000000 08:02 2893563                    /usr/lib/x86_64-linux-gnu/libschroedinger-1.0.so.0.11.0
7fc4b9cf3000-7fc4b9ef3000 ---p 000c0000 08:02 2893563                    /usr/lib/x86_64-linux-gnu/libschroedinger-1.0.so.0.11.0
7fc4b9ef3000-7fc4b9ef5000 r--p 000c0000 08:02 2893563                    /usr/lib/x86_64-linux-gnu/libschroedinger-1.0.so.0.11.0
7fc4b9ef5000-7fc4b9ef6000 rw-p 000c2000 08:02 2893563                    /usr/lib/x86_64-linux-gnu/libschroedinger-1.0.so.0.11.0
7fc4b9ef6000-7fc4b9ef7000 rw-p 00000000 00:00 0 
7fc4b9ef7000-7fc4b9f0e000 r-xp 00000000 08:02 2892100                    /usr/lib/x86_64-linux-gnu/libspeex.so.1.5.0
7fc4b9f0e000-7fc4ba10e000 ---p 00017000 08:02 2892100                    /usr/lib/x86_64-linux-gnu/libspeex.so.1.5.0
7fc4ba10e000-7fc4ba10f000 r--p 00017000 08:02 2892100                    /usr/lib/x86_64-linux-gnu/libspeex.so.1.5.0
7fc4ba10f000-7fc4ba110000 rw-p 00018000 08:02 2892100                    /usr/lib/x86_64-linux-gnu/libspeex.so.1.5.0
7fc4ba110000-7fc4ba128000 r-xp 00000000 08:02 2892140                    /usr/lib/x86_64-linux-gnu/libtheoradec.so.1.1.4
7fc4ba128000-7fc4ba327000 ---p 00018000 08:02 2892140                    /usr/lib/x86_64-linux-gnu/libtheoradec.so.1.1.4
7fc4ba327000-7fc4ba328000 r--p 00017000 08:02 2892140                    /usr/lib/x86_64-linux-gnu/libtheoradec.so.1.1.4
7fc4ba328000-7fc4ba329000 rw-p 00018000 08:02 2892140                    /usr/lib/x86_64-linux-gnu/libtheoradec.so.1.1.4
7fc4ba329000-7fc4ba368000 r-xp 00000000 08:02 2892142                    /usr/lib/x86_64-linux-gnu/libtheoraenc.so.1.1.2
7fc4ba368000-7fc4ba567000 ---p 0003f000 08:02 2892142                    /usr/lib/x86_64-linux-gnu/libtheoraenc.so.1.1.2
7fc4ba567000-7fc4ba568000 r--p 0003e000 08:02 2892142                    /usr/lib/x86_64-linux-gnu/libtheoraenc.so.1.1.2
7fc4ba568000-7fc4ba569000 rw-p 0003f000 08:02 2892142                    /usr/lib/x86_64-linux-gnu/libtheoraenc.so.1.1.2
7fc4ba569000-7fc4ba595000 r-xp 00000000 08:02 2892203                    /usr/lib/x86_64-linux-gnu/libvorbis.so.0.4.5
7fc4ba595000-7fc4ba794000 ---p 0002c000 08:02 2892203                    /usr/lib/x86_64-linux-gnu/libvorbis.so.0.4.5
7fc4ba794000-7fc4ba795000 r--p 0002b000 08:02 2892203                    /usr/lib/x86_64-linux-gnu/libvorbis.so.0.4.5
7fc4ba795000-7fc4ba796000 rw-p 0002c000 08:02 2892203                    /usr/lib/x86_64-linux-gnu/libvorbis.so.0.4.5
7fc4ba796000-7fc4baa49000 r-xp 00000000 08:02 2892205                    /usr/lib/x86_64-linux-gnu/libvorbisenc.so.2.0.8
7fc4baa49000-7fc4bac48000 ---p 002b3000 08:02 2892205                    /usr/lib/x86_64-linux-gnu/libvorbisenc.so.2.0.8
7fc4bac48000-7fc4bac64000 r--p 002b2000 08:02 2892205                    /usr/lib/x86_64-linux-gnu/libvorbisenc.so.2.0.8
7fc4bac64000-7fc4bac65000 rw-p 002ce000 08:02 2892205                    /usr/lib/x86_64-linux-gnu/libvorbisenc.so.2.0.8
7fc4bac65000-7fc4badfd000 r-xp 00000000 08:02 2892210                    /usr/lib/x86_64-linux-gnu/libvpx.so.1.3.0
7fc4badfd000-7fc4baffc000 ---p 00198000 08:02 2892210                    /usr/lib/x86_64-linux-gnu/libvpx.so.1.3.0
7fc4baffc000-7fc4baffe000 rw-p 00197000 08:02 2892210                    /usr/lib/x86_64-linux-gnu/libvpx.so.1.3.0
7fc4baffe000-7fc4bb044000 rw-p 00000000 00:00 0 
7fc4bb044000-7fc4bb15d000 r-xp 00000000 08:02 2893567                    /usr/lib/x86_64-linux-gnu/libx264.so.142
7fc4bb15d000-7fc4bb35d000 ---p 00119000 08:02 2893567                    /usr/lib/x86_64-linux-gnu/libx264.so.142
7fc4bb35d000-7fc4bb35e000 r--p 00119000 08:02 2893567                    /usr/lib/x86_64-linux-gnu/libx264.so.142
7fc4bb35e000-7fc4bb35f000 rw-p 0011a000 08:02 2893567                    /usr/lib/x86_64-linux-gnu/libx264.so.142
7fc4bb35f000-7fc4bb3da000 rw-p 00000000 00:00 0 
7fc4bb3da000-7fc4bb4a5000 r-xp 00000000 08:02 2893568                    /usr/lib/x86_64-linux-gnu/libxvidcore.so.4.3
7fc4bb4a5000-7fc4bb6a4000 ---p 000cb000 08:02 2893568                    /usr/lib/x86_64-linux-gnu/libxvidcore.so.4.3
7fc4bb6a4000-7fc4bb6a5000 r--p 000ca000 08:02 2893568                    /usr/lib/x86_64-linux-gnu/libxvidcore.so.4.3
7fc4bb6a5000-7fc4bb6af000 rw-p 000cb000 08:02 2893568                    /usr/lib/x86_64-linux-gnu/libxvidcore.so.4.3
7fc4bb6af000-7fc4bb718000 rw-p 00000000 00:00 0 
7fc4bb718000-7fc4bb75b000 r-xp 00000000 08:02 2891810                    /usr/lib/x86_64-linux-gnu/libjpeg.so.8.0.2
7fc4bb75b000-7fc4bb95b000 ---p 00043000 08:02 2891810                    /usr/lib/x86_64-linux-gnu/libjpeg.so.8.0.2
7fc4bb95b000-7fc4bb95c000 r--p 00043000 08:02 2891810                    /usr/lib/x86_64-linux-gnu/libjpeg.so.8.0.2
7fc4bb95c000-7fc4bb95d000 rw-p 00044000 08:02 2891810                    /usr/lib/x86_64-linux-gnu/libjpeg.so.8.0.2
7fc4bb95d000-7fc4bb96d000 rw-p 00000000 00:00 0 
7fc4bb96d000-7fc4bb983000 r-xp 00000000 08:02 24383540                   /lib/x86_64-linux-gnu/libgcc_s.so.1
7fc4bb983000-7fc4bbb82000 ---p 00016000 08:02 24383540                   /lib/x86_64-linux-gnu/libgcc_s.so.1
7fc4bbb82000-7fc4bbb83000 rw-p 00015000 08:02 24383540                   /lib/x86_64-linux-gnu/libgcc_s.so.1
7fc4bbb83000-7fc4bbbb5000 r-xp 00000000 08:02 2893926                    /usr/lib/x86_64-linux-gnu/libdc1394.so.22.1.8
7fc4bbbb5000-7fc4bbdb5000 ---p 00032000 08:02 2893926                    /usr/lib/x86_64-linux-gnu/libdc1394.so.22.1.8
7fc4bbdb5000-7fc4bbdb6000 r--p 00032000 08:02 2893926                    /usr/lib/x86_64-linux-gnu/libdc1394.so.22.1.8
7fc4bbdb6000-7fc4bbdb7000 rw-p 00033000 08:02 2893926                    /usr/lib/x86_64-linux-gnu/libdc1394.so.22.1.8
7fc4bbdb7000-7fc4bbdf7000 rw-p 00000000 00:00 0 
7fc4bbdf7000-7fc4bbefd000 r-xp 00000000 08:02 24383594                   /lib/x86_64-linux-gnu/libglib-2.0.so.0.4002.0
7fc4bbefd000-7fc4bc0fc000 ---p 00106000 08:02 24383594                   /lib/x86_64-linux-gnu/libglib-2.0.so.0.4002.0
7fc4bc0fc000-7fc4bc0fd000 r--p 00105000 08:02 24383594                   /lib/x86_64-linux-gnu/libglib-2.0.so.0.4002.0
7fc4bc0fd000-7fc4bc0fe000 rw-p 00106000 08:02 24383594                   /lib/x86_64-linux-gnu/libglib-2.0.so.0.4002.0
7fc4bc0fe000-7fc4bc0ff000 rw-p 00000000 00:00 0 
7fc4bc0ff000-7fc4bc14e000 r-xp 00000000 08:02 2891621                    /usr/lib/x86_64-linux-gnu/libgobject-2.0.so.0.4002.0
7fc4bc14e000-7fc4bc34e000 ---p 0004f000 08:02 2891621                    /usr/lib/x86_64-linux-gnu/libgobject-2.0.so.0.4002.0
7fc4bc34e000-7fc4bc34f000 r--p 0004f000 08:02 2891621                    /usr/lib/x86_64-linux-gnu/libgobject-2.0.so.0.4002.0
7fc4bc34f000-7fc4bc350000 rw-p 00050000 08:02 2891621                    /usr/lib/x86_64-linux-gnu/libgobject-2.0.so.0.4002.0
7fc4bc350000-7fc4bc456000 r-xp 00000000 08:02 2891341                    /usr/lib/x86_64-linux-gnu/libcairo.so.2.11301.0
7fc4bc456000-7fc4bc655000 ---p 00106000 08:02 2891341                    /usr/lib/x86_64-linux-gnu/libcairo.so.2.11301.0
7fc4bc655000-7fc4bc658000 r--p 00105000 08:02 2891341                    /usr/lib/x86_64-linux-gnu/libcairo.so.2.11301.0
7fc4bc658000-7fc4bc659000 rw-p 00108000 08:02 2891341                    /usr/lib/x86_64-linux-gnu/libcairo.so.2.11301.0
7fc4bc659000-7fc4bc65b000 rw-p 00000000 00:00 0 
7fc4bc65b000-7fc4bc67b000 r-xp 00000000 08:02 2887417                    /usr/lib/x86_64-linux-gnu/libgdk_pixbuf-2.0.so.0.3000.7
7fc4bc67b000-7fc4bc87a000 ---p 00020000 08:02 2887417                    /usr/lib/x86_64-linux-gnu/libgdk_pixbuf-2.0.so.0.3000.7
7fc4bc87a000-7fc4bc87b000 r--p 0001f000 08:02 2887417                    /usr/lib/x86_64-linux-gnu/libgdk_pixbuf-2.0.so.0.3000.7
7fc4bc87b000-7fc4bc87c000 rw-p 00020000 08:02 2887417                    /usr/lib/x86_64-linux-gnu/libgdk_pixbuf-2.0.so.0.3000.7
7fc4bc87c000-7fc4bc929000 r-xp 00000000 08:02 2887428                    /usr/lib/x86_64-linux-gnu/libgdk-x11-2.0.so.0.2400.23
7fc4bc929000-7fc4bcb29000 ---p 000ad000 08:02 2887428                    /usr/lib/x86_64-linux-gnu/libgdk-x11-2.0.so.0.2400.23
7fc4bcb29000-7fc4bcb2d000 r--p 000ad000 08:02 2887428                    /usr/lib/x86_64-linux-gnu/libgdk-x11-2.0.so.0.2400.23
7fc4bcb2d000-7fc4bcb2f000 rw-p 000b1000 08:02 2887428                    /usr/lib/x86_64-linux-gnu/libgdk-x11-2.0.so.0.2400.23
7fc4bcb2f000-7fc4bcf5f000 r-xp 00000000 08:02 2887429                    /usr/lib/x86_64-linux-gnu/libgtk-x11-2.0.so.0.2400.23
7fc4bcf5f000-7fc4bd15e000 ---p 00430000 08:02 2887429                    /usr/lib/x86_64-linux-gnu/libgtk-x11-2.0.so.0.2400.23
7fc4bd15e000-7fc4bd165000 r--p 0042f000 08:02 2887429                    /usr/lib/x86_64-linux-gnu/libgtk-x11-2.0.so.0.2400.23
7fc4bd165000-7fc4bd169000 rw-p 00436000 08:02 2887429                    /usr/lib/x86_64-linux-gnu/libgtk-x11-2.0.so.0.2400.23
7fc4bd169000-7fc4bd16c000 rw-p 00000000 00:00 0 
7fc4bd16c000-7fc4bd1b1000 r-xp 00000000 08:02 2885962                    /usr/lib/x86_64-linux-gnu/libswscale.so.2.1.1
7fc4bd1b1000-7fc4bd3b1000 ---p 00045000 08:02 2885962                    /usr/lib/x86_64-linux-gnu/libswscale.so.2.1.1
7fc4bd3b1000-7fc4bd3b2000 r--p 00045000 08:02 2885962                    /usr/lib/x86_64-linux-gnu/libswscale.so.2.1.1
7fc4bd3b2000-7fc4bd3b3000 rw-p 00046000 08:02 2885962                    /usr/lib/x86_64-linux-gnu/libswscale.so.2.1.1
7fc4bd3b3000-7fc4bfcda000 r-xp 00000000 08:02 8781826                    /usr/local/share/OpenCV/java/libopencv_java3.so
7fc4bfcda000-7fc4bfeda000 ---p 02927000 08:02 8781826                    /usr/local/share/OpenCV/java/libopencv_java3.so
7fc4bfeda000-7fc4bff0b000 r--p 02927000 08:02 8781826                    /usr/local/share/OpenCV/java/libopencv_java3.so
7fc4bff0b000-7fc4bff60000 rw-p 02958000 08:02 8781826                    /usr/local/share/OpenCV/java/libopencv_java3.so
7fc4bff60000-7fc4c0000000 rw-p 00000000 00:00 0 
7fc4c0000000-7fc4c0021000 rw-p 00000000 00:00 0 
7fc4c0021000-7fc4c4000000 ---p 00000000 00:00 0 
7fc4c4000000-7fc4c4021000 rw-p 00000000 00:00 0 
7fc4c4021000-7fc4c8000000 ---p 00000000 00:00 0 
7fc4c8000000-7fc4c8021000 rw-p 00000000 00:00 0 
7fc4c8021000-7fc4cc000000 ---p 00000000 00:00 0 
7fc4cc000000-7fc4cc021000 rw-p 00000000 00:00 0 
7fc4cc021000-7fc4d0000000 ---p 00000000 00:00 0 
7fc4d0000000-7fc4d0021000 rw-p 00000000 00:00 0 
7fc4d0021000-7fc4d4000000 ---p 00000000 00:00 0 
7fc4d4000000-7fc4d4021000 rw-p 00000000 00:00 0 
7fc4d4021000-7fc4d8000000 ---p 00000000 00:00 0 
7fc4d8000000-7fc4d8021000 rw-p 00000000 00:00 0 
7fc4d8021000-7fc4dc000000 ---p 00000000 00:00 0 
7fc4dc000000-7fc4dc021000 rw-p 00000000 00:00 0 
7fc4dc021000-7fc4e0000000 ---p 00000000 00:00 0 
7fc4e0000000-7fc4e008a000 rw-p 00000000 00:00 0 
7fc4e008a000-7fc4e4000000 ---p 00000000 00:00 0 
7fc4e4000000-7fc4e415a000 rw-p 00000000 00:00 0 
7fc4e415a000-7fc4e8000000 ---p 00000000 00:00 0 
7fc4e8000000-7fc4e819c000 rw-p 00000000 00:00 0 
7fc4e819c000-7fc4ec000000 ---p 00000000 00:00 0 
7fc4ec000000-7fc4ec090000 rw-p 00000000 00:00 0 
7fc4ec090000-7fc4f0000000 ---p 00000000 00:00 0 
7fc4f0000000-7fc4f0021000 rw-p 00000000 00:00 0 
7fc4f0021000-7fc4f4000000 ---p 00000000 00:00 0 
7fc4f4000000-7fc4f4021000 rw-p 00000000 00:00 0 
7fc4f4021000-7fc4f8000000 ---p 00000000 00:00 0 
7fc4f80ad000-7fc4f80b0000 ---p 00000000 00:00 0 
7fc4f80b0000-7fc4f81ae000 rwxp 00000000 00:00 0 
7fc4f81ae000-7fc4f81cc000 r-xp 00000000 08:02 2886011                    /usr/lib/x86_64-linux-gnu/libavutil.so.52.3.0
7fc4f81cc000-7fc4f83cc000 ---p 0001e000 08:02 2886011                    /usr/lib/x86_64-linux-gnu/libavutil.so.52.3.0
7fc4f83cc000-7fc4f83ce000 r--p 0001e000 08:02 2886011                    /usr/lib/x86_64-linux-gnu/libavutil.so.52.3.0
7fc4f83ce000-7fc4f83cf000 rw-p 00020000 08:02 2886011                    /usr/lib/x86_64-linux-gnu/libavutil.so.52.3.0
7fc4f83cf000-7fc4f83d3000 rw-p 00000000 00:00 0 
7fc4f83d3000-7fc4f84e1000 r-xp 00000000 08:02 2886071                    /usr/lib/x86_64-linux-gnu/libavformat.so.54.20.4
7fc4f84e1000-7fc4f86e0000 ---p 0010e000 08:02 2886071                    /usr/lib/x86_64-linux-gnu/libavformat.so.54.20.4
7fc4f86e0000-7fc4f86e8000 r--p 0010d000 08:02 2886071                    /usr/lib/x86_64-linux-gnu/libavformat.so.54.20.4
7fc4f86e8000-7fc4f86f5000 rw-p 00115000 08:02 2886071                    /usr/lib/x86_64-linux-gnu/libavformat.so.54.20.4
7fc4f86f5000-7fc4f8ca9000 r-xp 00000000 08:02 2886074                    /usr/lib/x86_64-linux-gnu/libavcodec.so.54.35.1
7fc4f8ca9000-7fc4f8ea9000 ---p 005b4000 08:02 2886074                    /usr/lib/x86_64-linux-gnu/libavcodec.so.54.35.1
7fc4f8ea9000-7fc4f8ebc000 r--p 005b4000 08:02 2886074                    /usr/lib/x86_64-linux-gnu/libavcodec.so.54.35.1
7fc4f8ebc000-7fc4f8ed4000 rw-p 005c7000 08:02 2886074                    /usr/lib/x86_64-linux-gnu/libavcodec.so.54.35.1
7fc4f8ed4000-7fc4f944a000 rw-p 00000000 00:00 0 
7fc4f944a000-7fc4f9462000 r-xp 00000000 08:02 24383726                   /lib/x86_64-linux-gnu/libz.so.1.2.8
7fc4f9462000-7fc4f9661000 ---p 00018000 08:02 24383726                   /lib/x86_64-linux-gnu/libz.so.1.2.8
7fc4f9661000-7fc4f9662000 r--p 00017000 08:02 24383726                   /lib/x86_64-linux-gnu/libz.so.1.2.8
7fc4f9662000-7fc4f9663000 rw-p 00018000 08:02 24383726                   /lib/x86_64-linux-gnu/libz.so.1.2.8
7fc4f9663000-7fc4f96af000 r-xp 00000000 08:02 2887420                    /usr/lib/x86_64-linux-gnu/libjasper.so.1.0.0
7fc4f96af000-7fc4f98ae000 ---p 0004c000 08:02 2887420                    /usr/lib/x86_64-linux-gnu/libjasper.so.1.0.0
7fc4f98ae000-7fc4f98af000 r--p 0004b000 08:02 2887420                    /usr/lib/x86_64-linux-gnu/libjasper.so.1.0.0
7fc4f98af000-7fc4f98b3000 rw-p 0004c000 08:02 2887420                    /usr/lib/x86_64-linux-gnu/libjasper.so.1.0.0
7fc4f98b3000-7fc4f98ba000 rw-p 00000000 00:00 0 
7fc4f98ba000-7fc4f98df000 r-xp 00000000 08:02 24383649                   /lib/x86_64-linux-gnu/libpng12.so.0.50.0
7fc4f98df000-7fc4f9ade000 ---p 00025000 08:02 24383649                   /lib/x86_64-linux-gnu/libpng12.so.0.50.0
7fc4f9ade000-7fc4f9adf000 r--p 00024000 08:02 24383649                   /lib/x86_64-linux-gnu/libpng12.so.0.50.0
7fc4f9adf000-7fc4f9ae0000 rw-p 00025000 08:02 24383649                   /lib/x86_64-linux-gnu/libpng12.so.0.50.0
7fc4f9ae0000-7fc4f9b03000 r-xp 00000000 08:02 2888699                    /usr/lib/x86_64-linux-gnu/libjpeg.so.62.0.0
7fc4f9b03000-7fc4f9d03000 ---p 00023000 08:02 2888699                    /usr/lib/x86_64-linux-gnu/libjpeg.so.62.0.0
7fc4f9d03000-7fc4f9d04000 r--p 00023000 08:02 2888699                    /usr/lib/x86_64-linux-gnu/libjpeg.so.62.0.0
7fc4f9d04000-7fc4f9d05000 rw-p 00024000 08:02 2888699                    /usr/lib/x86_64-linux-gnu/libjpeg.so.62.0.0
7fc4f9d05000-7fc4f9deb000 r-xp 00000000 08:02 2885811                    /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.19
7fc4f9deb000-7fc4f9fea000 ---p 000e6000 08:02 2885811                    /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.19
7fc4f9fea000-7fc4f9ff2000 r--p 000e5000 08:02 2885811                    /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.19
7fc4f9ff2000-7fc4f9ff4000 rw-p 000ed000 08:02 2885811                    /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.19
7fc4f9ff4000-7fc4fa009000 rw-p 00000000 00:00 0 
7fc4fa012000-7fc4fa015000 ---p 00000000 00:00 0 
7fc4fa015000-7fc4fa113000 rwxp 00000000 00:00 0 
7fc4fa113000-7fc4fa116000 ---p 00000000 00:00 0 
7fc4fa116000-7fc4fa214000 rwxp 00000000 00:00 0 
7fc4fa214000-7fc4fa230000 r--s 00393000 08:02 8260307                    /usr/lib/jvm/java-8-oracle/jre/lib/ext/cldrdata.jar
7fc4fa230000-7fc4fa23a000 r--s 00116000 08:02 8260301                    /usr/lib/jvm/java-8-oracle/jre/lib/ext/localedata.jar
7fc4fa23a000-7fc4fa23d000 ---p 00000000 00:00 0 
7fc4fa23d000-7fc4fa33b000 rwxp 00000000 00:00 0 
7fc4fa33b000-7fc4fa33e000 ---p 00000000 00:00 0 
7fc4fa33e000-7fc4fa43c000 rwxp 00000000 00:00 0 
7fc4fa43c000-7fc4fa43f000 ---p 00000000 00:00 0 
7fc4fa43f000-7fc4fa53d000 rwxp 00000000 00:00 0 
7fc4fa53d000-7fc4fa540000 ---p 00000000 00:00 0 
7fc4fa540000-7fc4fa63e000 rwxp 00000000 00:00 0 
7fc4fa63e000-7fc4fa641000 ---p 00000000 00:00 0 
7fc4fa641000-7fc4fa73f000 rwxp 00000000 00:00 0 
7fc4fa73f000-7fc4fa742000 ---p 00000000 00:00 0 
7fc4fa742000-7fc4fa840000 rwxp 00000000 00:00 0 
7fc4fa840000-7fc4fa86d000 r--s 00261000 08:02 43656030                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/transforms/jarMerging/debug/jars/1/1f/combined.jar (deleted)
7fc4fa86d000-7fc4fa94a000 r--s 01f70000 08:02 39330503                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-25.jar
7fc4fa94a000-7fc4faa26000 r--s 01f1a000 08:02 39330502                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-24.jar
7fc4faa26000-7fc4faafa000 r--s 0155f000 08:02 39327577                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-23.jar
7fc4faafa000-7fc4fabd9000 r--s 01723000 08:02 39330501                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-21.jar
7fc4fabd9000-7fc4fac9a000 r--s 01370000 08:02 39330500                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-19.jar
7fc4fac9a000-7fc4fad4a000 r--s 010e3000 08:02 39327613                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-17.jar
7fc4fad4a000-7fc4fadf3000 r--s 00faf000 08:02 39330499                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-14.jar
7fc4fadf3000-7fc4fae0a000 r-xp 00000000 08:02 24379397                   /lib/x86_64-linux-gnu/libresolv-2.19.so
7fc4fae0a000-7fc4fb00a000 ---p 00017000 08:02 24379397                   /lib/x86_64-linux-gnu/libresolv-2.19.so
7fc4fb00a000-7fc4fb00b000 r--p 00017000 08:02 24379397                   /lib/x86_64-linux-gnu/libresolv-2.19.so
7fc4fb00b000-7fc4fb00c000 rw-p 00018000 08:02 24379397                   /lib/x86_64-linux-gnu/libresolv-2.19.so
7fc4fb00c000-7fc4fb00e000 rw-p 00000000 00:00 0 
7fc4fb00e000-7fc4fb013000 r-xp 00000000 08:02 24379489                   /lib/x86_64-linux-gnu/libnss_dns-2.19.so
7fc4fb013000-7fc4fb212000 ---p 00005000 08:02 24379489                   /lib/x86_64-linux-gnu/libnss_dns-2.19.so
7fc4fb212000-7fc4fb213000 r--p 00004000 08:02 24379489                   /lib/x86_64-linux-gnu/libnss_dns-2.19.so
7fc4fb213000-7fc4fb214000 rw-p 00005000 08:02 24379489                   /lib/x86_64-linux-gnu/libnss_dns-2.19.so
7fc4fb214000-7fc4fb216000 r-xp 00000000 08:02 24383644                   /lib/x86_64-linux-gnu/libnss_mdns4_minimal.so.2
7fc4fb216000-7fc4fb415000 ---p 00002000 08:02 24383644                   /lib/x86_64-linux-gnu/libnss_mdns4_minimal.so.2
7fc4fb415000-7fc4fb416000 r--p 00001000 08:02 24383644                   /lib/x86_64-linux-gnu/libnss_mdns4_minimal.so.2
7fc4fb416000-7fc4fb417000 rw-p 00002000 08:02 24383644                   /lib/x86_64-linux-gnu/libnss_mdns4_minimal.so.2
7fc4fb417000-7fc4fb42d000 r-xp 00000000 08:02 8655943                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libnet.so
7fc4fb42d000-7fc4fb62d000 ---p 00016000 08:02 8655943                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libnet.so
7fc4fb62d000-7fc4fb62e000 rw-p 00016000 08:02 8655943                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libnet.so
7fc4fb62e000-7fc4fb62f000 ---p 00000000 00:00 0 
7fc4fb62f000-7fc4fb72f000 rwxp 00000000 00:00 0 
7fc4fb72f000-7fc4fb732000 ---p 00000000 00:00 0 
7fc4fb732000-7fc4fb830000 rwxp 00000000 00:00 0 
7fc4fb830000-7fc4fc000000 r--p 00000000 08:02 2890254                    /usr/lib/locale/locale-archive
7fc4fc000000-7fc4fc021000 rw-p 00000000 00:00 0 
7fc4fc021000-7fc500000000 ---p 00000000 00:00 0 
7fc500002000-7fc500009000 r--s 00000000 08:02 3280539                    /usr/lib/x86_64-linux-gnu/gconv/gconv-modules.cache
7fc500009000-7fc500016000 r--s 000af000 08:02 39329553                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/luni.jar
7fc500016000-7fc50003d000 rw-p 00000000 00:00 0 
7fc50003d000-7fc500040000 ---p 00000000 00:00 0 
7fc500040000-7fc50013e000 rwxp 00000000 00:00 0 
7fc50013e000-7fc500141000 ---p 00000000 00:00 0 
7fc500141000-7fc50023f000 rwxp 00000000 00:00 0 
7fc50023f000-7fc500242000 ---p 00000000 00:00 0 
7fc500242000-7fc500340000 rwxp 00000000 00:00 0 
7fc500340000-7fc508000000 rw-p 00000000 00:00 0 
7fc508000000-7fc508021000 rw-p 00000000 00:00 0 
7fc508021000-7fc50c000000 ---p 00000000 00:00 0 
7fc50c000000-7fc50c11a000 rw-p 00000000 00:00 0 
7fc50c11a000-7fc510000000 ---p 00000000 00:00 0 
7fc510000000-7fc510021000 rw-p 00000000 00:00 0 
7fc510021000-7fc514000000 ---p 00000000 00:00 0 
7fc514000000-7fc514021000 rw-p 00000000 00:00 0 
7fc514021000-7fc518000000 ---p 00000000 00:00 0 
7fc518000000-7fc518021000 rw-p 00000000 00:00 0 
7fc518021000-7fc51c000000 ---p 00000000 00:00 0 
7fc51c000000-7fc51c021000 rw-p 00000000 00:00 0 
7fc51c021000-7fc520000000 ---p 00000000 00:00 0 
7fc520000000-7fc520021000 rw-p 00000000 00:00 0 
7fc520021000-7fc524000000 ---p 00000000 00:00 0 
7fc524000000-7fc524021000 rw-p 00000000 00:00 0 
7fc524021000-7fc528000000 ---p 00000000 00:00 0 
7fc528000000-7fc528021000 rw-p 00000000 00:00 0 
7fc528021000-7fc52c000000 ---p 00000000 00:00 0 
7fc52c004000-7fc52c009000 r--s 00055000 08:02 39330751                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/opencv-320.jar
7fc52c009000-7fc52c0d0000 rw-p 00000000 00:00 0 
7fc52c0d0000-7fc52c0d3000 ---p 00000000 00:00 0 
7fc52c0d3000-7fc52c1d1000 rwxp 00000000 00:00 0 
7fc52c1d1000-7fc52c1d4000 ---p 00000000 00:00 0 
7fc52c1d4000-7fc52c2d2000 rwxp 00000000 00:00 0 
7fc52c2d2000-7fc52c2d5000 ---p 00000000 00:00 0 
7fc52c2d5000-7fc52c3d3000 rwxp 00000000 00:00 0 
7fc52c3d3000-7fc52c3d6000 ---p 00000000 00:00 0 
7fc52c3d6000-7fc52c4d4000 rwxp 00000000 00:00 0 
7fc52c4d4000-7fc52c4d5000 ---p 00000000 00:00 0 
7fc52c4d5000-7fc52c5d5000 rwxp 00000000 00:00 0 
7fc52c5d5000-7fc52e1f8000 rw-p 00000000 00:00 0 
7fc52e1f8000-7fc52e3d1000 r--s 03d3e000 08:02 8260296                    /usr/lib/jvm/java-8-oracle/jre/lib/rt.jar
7fc52e3d1000-7fc52eac7000 rw-p 00000000 00:00 0 
7fc52eac7000-7fc52ec47000 ---p 00000000 00:00 0 
7fc52ec47000-7fc52f413000 rw-p 00000000 00:00 0 
7fc52f413000-7fc52f414000 ---p 00000000 00:00 0 
7fc52f414000-7fc52f514000 rwxp 00000000 00:00 0 
7fc52f514000-7fc52f515000 ---p 00000000 00:00 0 
7fc52f515000-7fc52f615000 rwxp 00000000 00:00 0 
7fc52f615000-7fc52f616000 ---p 00000000 00:00 0 
7fc52f616000-7fc52f716000 rwxp 00000000 00:00 0 
7fc52f716000-7fc52f717000 ---p 00000000 00:00 0 
7fc52f717000-7fc52f817000 rwxp 00000000 00:00 0 
7fc52f817000-7fc52f818000 ---p 00000000 00:00 0 
7fc52f818000-7fc52f918000 rwxp 00000000 00:00 0 
7fc52f918000-7fc52f919000 ---p 00000000 00:00 0 
7fc52f919000-7fc52fa19000 rwxp 00000000 00:00 0 
7fc52fa19000-7fc52fa1a000 ---p 00000000 00:00 0 
7fc52fa1a000-7fc52fb1a000 rwxp 00000000 00:00 0 
7fc52fb1a000-7fc52fb6e000 rw-p 00000000 00:00 0 
7fc52fb6e000-7fc53004d000 ---p 00000000 00:00 0 
7fc53004d000-7fc5300a1000 rw-p 00000000 00:00 0 
7fc5300a1000-7fc53057f000 ---p 00000000 00:00 0 
7fc53057f000-7fc5305aa000 rw-p 00000000 00:00 0 
7fc5305aa000-7fc530819000 ---p 00000000 00:00 0 
7fc530819000-7fc530824000 rw-p 00000000 00:00 0 
7fc530824000-7fc530bda000 ---p 00000000 00:00 0 
7fc530bda000-7fc530e4a000 rwxp 00000000 00:00 0 
7fc530e4a000-7fc53fbda000 ---p 00000000 00:00 0 
7fc53fbda000-7fc53fbf4000 r-xp 00000000 08:02 8655981                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libzip.so
7fc53fbf4000-7fc53fdf4000 ---p 0001a000 08:02 8655981                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libzip.so
7fc53fdf4000-7fc53fdf5000 rw-p 0001a000 08:02 8655981                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libzip.so
7fc53fdf5000-7fc53fdff000 r-xp 00000000 08:02 24379495                   /lib/x86_64-linux-gnu/libnss_files-2.19.so
7fc53fdff000-7fc53fffe000 ---p 0000a000 08:02 24379495                   /lib/x86_64-linux-gnu/libnss_files-2.19.so
7fc53fffe000-7fc53ffff000 r--p 00009000 08:02 24379495                   /lib/x86_64-linux-gnu/libnss_files-2.19.so
7fc53ffff000-7fc540000000 rw-p 0000a000 08:02 24379495                   /lib/x86_64-linux-gnu/libnss_files-2.19.so
7fc540000000-7fc54051e000 rw-p 00000000 00:00 0 
7fc54051e000-7fc544000000 ---p 00000000 00:00 0 
7fc544002000-7fc544074000 rw-p 00000000 00:00 0 
7fc544074000-7fc544075000 ---p 00000000 00:00 0 
7fc544075000-7fc544175000 rwxp 00000000 00:00 0 
7fc544175000-7fc544180000 r-xp 00000000 08:02 24379487                   /lib/x86_64-linux-gnu/libnss_nis-2.19.so
7fc544180000-7fc54437f000 ---p 0000b000 08:02 24379487                   /lib/x86_64-linux-gnu/libnss_nis-2.19.so
7fc54437f000-7fc544380000 r--p 0000a000 08:02 24379487                   /lib/x86_64-linux-gnu/libnss_nis-2.19.so
7fc544380000-7fc544381000 rw-p 0000b000 08:02 24379487                   /lib/x86_64-linux-gnu/libnss_nis-2.19.so
7fc544381000-7fc544398000 r-xp 00000000 08:02 24379483                   /lib/x86_64-linux-gnu/libnsl-2.19.so
7fc544398000-7fc544597000 ---p 00017000 08:02 24379483                   /lib/x86_64-linux-gnu/libnsl-2.19.so
7fc544597000-7fc544598000 r--p 00016000 08:02 24379483                   /lib/x86_64-linux-gnu/libnsl-2.19.so
7fc544598000-7fc544599000 rw-p 00017000 08:02 24379483                   /lib/x86_64-linux-gnu/libnsl-2.19.so
7fc544599000-7fc54459b000 rw-p 00000000 00:00 0 
7fc54459b000-7fc5445a4000 r-xp 00000000 08:02 24379482                   /lib/x86_64-linux-gnu/libnss_compat-2.19.so
7fc5445a4000-7fc5447a3000 ---p 00009000 08:02 24379482                   /lib/x86_64-linux-gnu/libnss_compat-2.19.so
7fc5447a3000-7fc5447a4000 r--p 00008000 08:02 24379482                   /lib/x86_64-linux-gnu/libnss_compat-2.19.so
7fc5447a4000-7fc5447a5000 rw-p 00009000 08:02 24379482                   /lib/x86_64-linux-gnu/libnss_compat-2.19.so
7fc5447a5000-7fc5447d0000 r-xp 00000000 08:02 8655949                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libjava.so
7fc5447d0000-7fc5449cf000 ---p 0002b000 08:02 8655949                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libjava.so
7fc5449cf000-7fc5449d1000 rw-p 0002a000 08:02 8655949                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libjava.so
7fc5449d1000-7fc5449de000 r-xp 00000000 08:02 8655947                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libverify.so
7fc5449de000-7fc544bdd000 ---p 0000d000 08:02 8655947                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libverify.so
7fc544bdd000-7fc544bdf000 rw-p 0000c000 08:02 8655947                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/libverify.so
7fc544bdf000-7fc544be6000 r-xp 00000000 08:02 24379491                   /lib/x86_64-linux-gnu/librt-2.19.so
7fc544be6000-7fc544de5000 ---p 00007000 08:02 24379491                   /lib/x86_64-linux-gnu/librt-2.19.so
7fc544de5000-7fc544de6000 r--p 00006000 08:02 24379491                   /lib/x86_64-linux-gnu/librt-2.19.so
7fc544de6000-7fc544de7000 rw-p 00007000 08:02 24379491                   /lib/x86_64-linux-gnu/librt-2.19.so
7fc544de7000-7fc544eec000 r-xp 00000000 08:02 24379411                   /lib/x86_64-linux-gnu/libm-2.19.so
7fc544eec000-7fc5450eb000 ---p 00105000 08:02 24379411                   /lib/x86_64-linux-gnu/libm-2.19.so
7fc5450eb000-7fc5450ec000 r--p 00104000 08:02 24379411                   /lib/x86_64-linux-gnu/libm-2.19.so
7fc5450ec000-7fc5450ed000 rw-p 00105000 08:02 24379411                   /lib/x86_64-linux-gnu/libm-2.19.so
7fc5450ed000-7fc545dbb000 r-xp 00000000 08:02 8655923                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/server/libjvm.so
7fc545dbb000-7fc545fbb000 ---p 00cce000 08:02 8655923                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/server/libjvm.so
7fc545fbb000-7fc546094000 rw-p 00cce000 08:02 8655923                    /usr/lib/jvm/java-8-oracle/jre/lib/amd64/server/libjvm.so
7fc546094000-7fc5460df000 rw-p 00000000 00:00 0 
7fc5460df000-7fc546299000 r-xp 00000000 08:02 24379492                   /lib/x86_64-linux-gnu/libc-2.19.so
7fc546299000-7fc546499000 ---p 001ba000 08:02 24379492                   /lib/x86_64-linux-gnu/libc-2.19.so
7fc546499000-7fc54649d000 r--p 001ba000 08:02 24379492                   /lib/x86_64-linux-gnu/libc-2.19.so
7fc54649d000-7fc54649f000 rw-p 001be000 08:02 24379492                   /lib/x86_64-linux-gnu/libc-2.19.so
7fc54649f000-7fc5464a4000 rw-p 00000000 00:00 0 
7fc5464a4000-7fc5464a7000 r-xp 00000000 08:02 24379421                   /lib/x86_64-linux-gnu/libdl-2.19.so
7fc5464a7000-7fc5466a6000 ---p 00003000 08:02 24379421                   /lib/x86_64-linux-gnu/libdl-2.19.so
7fc5466a6000-7fc5466a7000 r--p 00002000 08:02 24379421                   /lib/x86_64-linux-gnu/libdl-2.19.so
7fc5466a7000-7fc5466a8000 rw-p 00003000 08:02 24379421                   /lib/x86_64-linux-gnu/libdl-2.19.so
7fc5466a8000-7fc5466bd000 r-xp 00000000 08:02 8260096                    /usr/lib/jvm/java-8-oracle/lib/amd64/jli/libjli.so
7fc5466bd000-7fc5468bd000 ---p 00015000 08:02 8260096                    /usr/lib/jvm/java-8-oracle/lib/amd64/jli/libjli.so
7fc5468bd000-7fc5468be000 rw-p 00015000 08:02 8260096                    /usr/lib/jvm/java-8-oracle/lib/amd64/jli/libjli.so
7fc5468be000-7fc5468d7000 r-xp 00000000 08:02 24379484                   /lib/x86_64-linux-gnu/libpthread-2.19.so
7fc5468d7000-7fc546ad6000 ---p 00019000 08:02 24379484                   /lib/x86_64-linux-gnu/libpthread-2.19.so
7fc546ad6000-7fc546ad7000 r--p 00018000 08:02 24379484                   /lib/x86_64-linux-gnu/libpthread-2.19.so
7fc546ad7000-7fc546ad8000 rw-p 00019000 08:02 24379484                   /lib/x86_64-linux-gnu/libpthread-2.19.so
7fc546ad8000-7fc546adc000 rw-p 00000000 00:00 0 
7fc546adc000-7fc546aff000 r-xp 00000000 08:02 24379485                   /lib/x86_64-linux-gnu/ld-2.19.so
7fc546aff000-7fc546b06000 r--s 00044000 08:02 39330752                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/rmi.jar
7fc546b06000-7fc546b09000 r--s 0001d000 08:02 39329551                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/log4j-api-2.1.jar
7fc546b09000-7fc546bda000 rw-p 00000000 00:00 0 
7fc546bda000-7fc546bdd000 ---p 00000000 00:00 0 
7fc546bdd000-7fc546cdb000 rwxp 00000000 00:00 0 
7fc546cdb000-7fc546cdf000 rw-p 00000000 00:00 0 
7fc546cdf000-7fc546ce4000 r--s 0005b000 08:02 39329397                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/kernel.jar
7fc546ce4000-7fc546ce5000 r--s 00007000 08:02 43656221                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/transforms/mergeJavaRes/debug/jars/2/1f/main.jar (deleted)
7fc546ce5000-7fc546cec000 r--s 00047000 08:02 43656433                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/multi-dex/debug/componentClasses.jar
7fc546cec000-7fc546cee000 r--s 0000c000 08:02 36049601                   /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/gradle/wrapper/gradle-wrapper.jar
7fc546cee000-7fc546cf2000 r--s 0009a000 08:02 8260272                    /usr/lib/jvm/java-8-oracle/jre/lib/jsse.jar
7fc546cf2000-7fc546cfa000 rw-s 00000000 08:02 56366541                   /tmp/hsperfdata_ubuntu/5901
7fc546cfa000-7fc546cfb000 rw-p 00000000 00:00 0 
7fc546cfb000-7fc546cfc000 r--p 00000000 00:00 0 
7fc546cfc000-7fc546cfe000 rw-p 00000000 00:00 0 
7fc546cfe000-7fc546cff000 r--p 00022000 08:02 24379485                   /lib/x86_64-linux-gnu/ld-2.19.so
7fc546cff000-7fc546d00000 rw-p 00023000 08:02 24379485                   /lib/x86_64-linux-gnu/ld-2.19.so
7fc546d00000-7fc546d01000 rw-p 00000000 00:00 0 
7ffe3a252000-7ffe3a272000 rwxp 00000000 00:00 0                          [stack]
7ffe3a272000-7ffe3a274000 rw-p 00000000 00:00 0 
7ffe3a2c3000-7ffe3a2c5000 r--p 00000000 00:00 0                          [vvar]
7ffe3a2c5000-7ffe3a2c7000 r-xp 00000000 00:00 0                          [vdso]
ffffffffff600000-ffffffffff601000 r-xp 00000000 00:00 0                  [vsyscall]

VM Arguments:
jvm_args: -Djava.library.path=/usr/local/share/OpenCV/java -Dfile.encoding=UTF-8 
java_command: sapphire.kernel.server.KernelServerImpl 157.82.159.46 22345 157.82.159.46 22346
java_class_path (initial): /home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/classes/debug:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-14.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-17.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-19.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-21.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-23.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-24.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/build/generated/mockable-android-25.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/gradle/wrapper/gradle-wrapper.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/multi-dex/debug/componentClasses.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/transforms/jarMerging/debug/jars/1/1f/combined.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/build/intermediates/transforms/mergeJavaRes/debug/jars/2/1f/main.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/kernel.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/log4j-api-2.1.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/luni.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/rmi.jar:/home/ubuntu/Sapphire/example_apps/AndroidStudioMinnie/sapphire/libs/opencv-320.jar
Launcher Type: SUN_STANDARD

Environment Variables:
JAVA_HOME=/usr/lib/jvm/java-8-oracle
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin:/home/ubuntu/android-studio/bin:/home/ubuntu/Downloads/p7zip_16.02/bin:/home/ubuntu/Downloads:/home/ubuntu/Android/Sdk/build-tools/25.0.2:/home/ubuntu/Downloads/dex2jar-2.0
SHELL=/bin/bash
DISPLAY=:0

Signal Handlers:
SIGSEGV: [libjvm.so+0xac78c0], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGBUS: [libjvm.so+0xac78c0], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGFPE: [libjvm.so+0x920ac0], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGPIPE: [libjvm.so+0x920ac0], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGXFSZ: [libjvm.so+0x920ac0], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGILL: [libjvm.so+0x920ac0], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGUSR1: SIG_DFL, sa_mask[0]=00000000000000000000000000000000, sa_flags=none
SIGUSR2: [libjvm.so+0x922300], sa_mask[0]=00100000000000000000000000000000, sa_flags=SA_RESTART|SA_SIGINFO
SIGHUP: [libjvm.so+0x923700], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGINT: [libjvm.so+0x923700], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGTERM: [libjvm.so+0x923700], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
SIGQUIT: [libjvm.so+0x923700], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO


---------------  S Y S T E M  ---------------

OS:DISTRIB_ID=Ubuntu
DISTRIB_RELEASE=14.04
DISTRIB_CODENAME=trusty
DISTRIB_DESCRIPTION="Ubuntu 14.04.5 LTS"

uname:Linux 4.4.0-59-generic #80~14.04.1-Ubuntu SMP Fri Jan 6 18:02:02 UTC 2017 x86_64
libc:glibc 2.19 NPTL 2.19 
rlimit: STACK 8192k, CORE 0k, NPROC 63472, NOFILE 4096, AS infinity
load average:1.36 1.02 0.69

/proc/meminfo:
MemTotal:       16343624 kB
MemFree:         5246276 kB
MemAvailable:    9709516 kB
Buffers:          529568 kB
Cached:          4843956 kB
SwapCached:            0 kB
Active:          7847136 kB
Inactive:        2700600 kB
Active(anon):    5175984 kB
Inactive(anon):   918908 kB
Active(file):    2671152 kB
Inactive(file):  1781692 kB
Unevictable:         596 kB
Mlocked:             596 kB
SwapTotal:      16688124 kB
SwapFree:       16688124 kB
Dirty:              4172 kB
Writeback:             0 kB
AnonPages:       5174856 kB
Mapped:           663388 kB
Shmem:            920684 kB
Slab:             396212 kB
SReclaimable:     346648 kB
SUnreclaim:        49564 kB
KernelStack:       15008 kB
PageTables:        53544 kB
NFS_Unstable:          0 kB
Bounce:                0 kB
WritebackTmp:          0 kB
CommitLimit:    24859936 kB
Committed_AS:   12280008 kB
VmallocTotal:   34359738367 kB
VmallocUsed:           0 kB
VmallocChunk:          0 kB
HardwareCorrupted:     0 kB
AnonHugePages:   2684928 kB
CmaTotal:              0 kB
CmaFree:               0 kB
HugePages_Total:       0
HugePages_Free:        0
HugePages_Rsvd:        0
HugePages_Surp:        0
Hugepagesize:       2048 kB
DirectMap4k:      161188 kB
DirectMap2M:     8138752 kB
DirectMap1G:     9437184 kB


CPU:total 8 (4 cores per cpu, 2 threads per core) family 6 model 60 stepping 3, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, clmul, erms, rtm, lzcnt, ht, tsc, tscinvbit, bmi1, bmi2

/proc/cpuinfo:
processor	: 0
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3499.742
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 0
cpu cores	: 4
apicid		: 0
initial apicid	: 0
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 1
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3456.046
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 1
cpu cores	: 4
apicid		: 2
initial apicid	: 2
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 2
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3453.656
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 2
cpu cores	: 4
apicid		: 4
initial apicid	: 4
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 3
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3399.867
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 3
cpu cores	: 4
apicid		: 6
initial apicid	: 6
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 4
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3400.398
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 0
cpu cores	: 4
apicid		: 1
initial apicid	: 1
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 5
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3400.531
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 1
cpu cores	: 4
apicid		: 3
initial apicid	: 3
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 6
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3379.281
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 2
cpu cores	: 4
apicid		: 5
initial apicid	: 5
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:

processor	: 7
vendor_id	: GenuineIntel
cpu family	: 6
model		: 60
model name	: Intel(R) Core(TM) i7-4770 CPU @ 3.40GHz
stepping	: 3
microcode	: 0x1a
cpu MHz		: 3459.101
cache size	: 8192 KB
physical id	: 0
siblings	: 8
core id		: 3
cpu cores	: 4
apicid		: 7
initial apicid	: 7
fpu		: yes
fpu_exception	: yes
cpuid level	: 13
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc aperfmperf eagerfpu pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm epb tpr_shadow vnmi flexpriority ept vpid fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm xsaveopt dtherm ida arat pln pts
bugs		:
bogomips	: 6783.69
clflush size	: 64
cache_alignment	: 64
address sizes	: 39 bits physical, 48 bits virtual
power management:



Memory: 4k page, physical 16343624k(5246276k free), swap 16688124k(16688124k free)

vm_info: Java HotSpot(TM) 64-Bit Server VM (25.121-b13) for linux-amd64 JRE (1.8.0_121-b13), built on Dec 12 2016 16:36:53 by "java_re" with gcc 4.3.0 20080428 (Red Hat 4.3.0-8)

time: Wed Mar 29 16:08:43 2017
elapsed time: 276 seconds (0d 0h 4m 36s)

