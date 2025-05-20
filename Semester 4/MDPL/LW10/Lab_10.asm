.386
.model flat,stdcall
option casemap:none 
include C:\masm32\include\windows.inc 
include C:\masm32\include\kernel32.inc 
include C:\masm32\include\fpu.inc 
includelib C:\masm32\lib\user32.lib 
includelib C:\masm32\lib\kernel32.lib 
includelib C:\masm32\lib\fpu.lib 
BSIZE equ 30 
.data 

d	dd	27.0
ce	dd	4.0
b	dd	3.0
x	dd	0.0
y	dd	1.0
_1div3	dd	0.33333333333333333333
one	dd	1.0
two	dd	2.0



result dd ?
stdout dd ?
cWritten dd ?
buf db BSIZE dup (?) 

.code 
start: 
main proc 
invoke GetStdHandle,STD_OUTPUT_HANDLE 
mov stdout,eax 
fld _1div3
fld d
fyl2x
fld st
frndint
fxch
fsub st,st(1)
f2xm1
fld1
fadd
fscale

fld ce
fadd

fld b
fld d
fmul
fadd
fsqrt

fld x
Fptan
fdiv
fld two
fmul
fld one
fld x
Fptan
fdiv
fld two
fmul
fadd
fdiv

fld one
fld x
fptan
fdiv
fld two
fmul
fsub
fld two
fld x
fptan
fdiv
fmul
fld one
fadd
fdiv

fadd
fdiv

fstp result
invoke FpuFLtoA,ADDR result,5,ADDR buf,SRC1_REAL or SRC2_DIMM
invoke WriteConsoleA,stdout,ADDR buf,BSIZE,ADDR cWritten,NULL 
invoke ExitProcess,0 
main endp 
end start
