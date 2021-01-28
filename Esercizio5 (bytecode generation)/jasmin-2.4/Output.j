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
 ldc 2
 istore 0
L1:
 ldc 3
 istore 1
L2:
 ldc 4
 istore 2
L3:
 ldc 5
 istore 3
L4:
 ldc 2
 istore 4
L5:
 ldc 1
 istore 5
L6:
 ldc 8
 istore 6
L7:
 ldc 9
 istore 7
L8:
 iload 0
 ldc 2
 iadd 
 invokestatic Output/print(I)V
L9:
 iload 0
 iload 1
 if_icmpgt L10
 goto L11
L10:
 iload 0
 iload 6
 if_icmpgt L14
 goto L15
L14:
 iload 0
 invokestatic Output/print(I)V
L18:
 invokestatic Output/read()I
 istore 0
L17:
 goto L16
L15:
 iload 0
 invokestatic Output/print(I)V
L16:
L13:
 goto L12
L11:
 iload 1
 iload 6
 if_icmpgt L19
 goto L20
L19:
 iload 1
 invokestatic Output/print(I)V
L22:
 goto L21
L20:
 iload 0
 invokestatic Output/print(I)V
L21:
L12:
L23:
 ldc 1
 invokestatic Output/print(I)V
 ldc 2
 ldc 3
 ldc 4
 iadd 
 iadd 
 invokestatic Output/print(I)V
 ldc 4
 invokestatic Output/print(I)V
 ldc 5
 invokestatic Output/print(I)V
 ldc 2
 invokestatic Output/print(I)V
L24:
 iload 0
 iload 1
 if_icmpgt L25
 goto L26
L25:
 iload 0
 invokestatic Output/print(I)V
L28:
 goto L27
L26:
 iload 1
 invokestatic Output/print(I)V
L27:
L29:
L30:
 iload 0
 ldc 0
 if_icmpgt L31
 goto L32
L31:
 iload 0
 ldc 1
 isub 
 istore 0
L33:
 iload 0
 invokestatic Output/print(I)V
 goto L30
L32:
L34:
L35:
 iload 2
 iload 3
 if_icmpne L36
 goto L37
L36:
 iload 2
 iload 3
 if_icmpgt L38
 goto L39
L38:
 iload 2
 iload 3
 isub 
 istore 2
L41:
 goto L40
L39:
 iload 3
 iload 2
 isub 
 istore 3
L40:
 goto L35
L37:
L42:
 iload 2
 invokestatic Output/print(I)V
L43:
L44:
 iload 4
 iload 7
 if_icmple L45
 goto L46
L45:
 iload 5
 iload 4
 imul 
 istore 5
L47:
 iload 4
 ldc 1
 iadd 
 istore 4
 goto L44
L46:
L48:
 iload 5
 invokestatic Output/print(I)V
L49:
 ldc 5
 ldc 7
 ldc 3
 ldc 10
 imul 
 isub 
 iadd 
 invokestatic Output/print(I)V
 ldc 3
 ldc 4
 isub 
 ldc 5
 imul 
 invokestatic Output/print(I)V
L50:
 invokestatic Output/read()I
 istore 0
L51:
 invokestatic Output/read()I
 istore 1
L52:
 invokestatic Output/read()I
 istore 6
L53:
 iload 0
 iload 1
 if_icmpne L54
 goto L55
L54:
 iload 0
 iload 6
 if_icmpgt L58
 goto L59
L58:
 iload 0
 invokestatic Output/print(I)V
L61:
 iload 1
 ldc 3
 if_icmpeq L58
 goto L59
L58:
 ldc 69
 invokestatic Output/print(I)V
L62:
 goto L60
L59:
 iload 6
 invokestatic Output/print(I)V
L60:
L57:
 goto L56
L55:
 iload 1
 iload 6
 if_icmpgt L64
 goto L65
L64:
 iload 1
 invokestatic Output/print(I)V
L67:
 goto L66
L65:
 iload 6
 invokestatic Output/print(I)V
L66:
L56:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

