package jgo.compiler
package transl

import interm._
import codeseq._
import instr._
import symbol._
import types._

import RuntimeInfo._

import org.objectweb.asm._
import commons._
import Opcodes._

class PkgTranslator(val interm: PkgInterm) extends TypeTranslation {
  val cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
  
  cw.visit(V1_6, ACC_PUBLIC, interm.target.name, null, "java/lang/Object", null)
  
  interm.globals foreach { global =>
    val access = if (global.isPublic) ACC_PUBLIC else 0
    val fieldVis = cw.visitField(access, global.name, toDesc(global.t), null, null)
    if (global.t.radix.isInstanceOf[UnsignedType])
      fieldVis.visitAnnotation(UnsignedAnnot, true)
    fieldVis.visitEnd()
  }
}