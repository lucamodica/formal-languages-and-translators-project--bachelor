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
 invokestatic Output/read()I
 istore 0
L1:
 invokestatic Output/read()I
 istore 1
L2:
 invokestatic Output/read()I
 istore 2
L3:
 iload 0
 iload 1
 if_icmpgt L7
 goto L5
L7:
 iload 0
 iload 2
 if_icmpgt L4
 goto L5
L4:
 iload 0
 istore 3
L8:
 iload 1
 iload 0
 if_icmpgt L9
 goto L5
L9:
 iload 1
 iload 2
 if_icmpgt L4
 goto L5
L4:
 iload 1
 istore 3
L10:
 goto L6
L5:
 iload 2
 istore 3
L6:
L12:
L13:
 iload 0
 iload 3
 if_icmplt L14
 goto L15
L14:
 iload 0
 ldc 1
 iadd 
 istore 0
 goto L13
L15:
L16:
L17:
 iload 1
 iload 3
 if_icmplt L18
 goto L19
L18:
 iload 1
 ldc 1
 iadd 
 istore 1
 goto L17
L19:
L20:
L21:
 iload 2
 iload 3
 if_icmplt L22
 goto L23
L22:
 iload 2
 ldc 1
 iadd 
 istore 2
 goto L21
L23:
L24:
 iload 0
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
 iload 2
 invokestatic Output/print(I)V
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

