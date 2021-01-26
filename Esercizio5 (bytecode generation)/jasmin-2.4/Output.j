.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 ldc 11
 invokestatic Output/print(I)V
 ldc 1
 ldc 3
 ldc 7
 iadd 
 iadd 
 invokestatic Output/print(I)V
 ldc 8
 invokestatic Output/print(I)V
L1:
 ldc 7
 istore 0
L2:
 iload 0
 ldc 5
 if_icmpgt L6
 goto L4
L6:
 iload 0
 ldc 5
 if_icmpgt L3
 goto L4
L3:
 iload 0
 invokestatic Output/print(I)V
L7:
 goto L5
L4:
 ldc 2
 invokestatic Output/print(I)V
L5:
L8:
 iload 0
 ldc 7
 if_icmpeq L10
 goto L9
L9:
 ldc 7
 invokestatic Output/print(I)V
L12:
 goto L11
L10:
 ldc 2
 invokestatic Output/print(I)V
L11:
L13:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

