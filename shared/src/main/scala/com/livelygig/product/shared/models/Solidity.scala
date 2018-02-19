package com.livelygig.product.shared.models

import scala.collection.generic.CanBuildFrom

object Solidity {

  trait SolidityType {
    protected val underlying: Any

    val typeName: String

    def typeMap[Base, Container](f: SolidityType => Base)(implicit bf: CanBuildFrom[Container, Either[Base, Container], Container]): Either[Base, Container] = {
      Left(f(this))
    }

    override def equals(that: Any): Boolean = that match {
      case that: SolidityType => underlying equals that.underlying
      case _ => underlying equals that
    }
    override def hashCode: Int = underlying.hashCode

    override def toString: String = underlying.toString

  }

  abstract class OrderedSolidityType[T](implicit ordering: Ordering[T]) extends Ordered[OrderedSolidityType[T]] with SolidityType {
    protected val underlying: T
    def compare(that: OrderedSolidityType[T]): Int = ordering.compare(underlying, that.underlying)
  }

  trait TypeName[T] {
    def typeName: String
  }

  final class Address(value: String) extends SolidityType {
    protected val underlying: String = value

    val typeName: String = Address.typeName
  }

  implicit object Address extends TypeName[Address] {
    def typeName = "address"
  }

  final class Uint(value: BigInt) extends OrderedSolidityType[BigInt] {
    protected val underlying: BigInt = value

    def this(value: String) = this(BigInt(value))

    val typeName: String = Uint.typeName
  }

  implicit object Uint extends TypeName[Uint] {
    def typeName = "uint"
  }

  implicit def intToUint(int: Int): Uint = new Uint(int)

  final class Array[T <: SolidityType : TypeName](value: Seq[T]) extends Seq[T] with SolidityType {
    protected val underlying: Seq[T] = value

    override def typeMap[Base, Container](f: SolidityType => Base)
                                         (implicit bf: CanBuildFrom[Container, Either[Base, Container], Container]): Either[Base, Container] = {
      val x: Seq[Either[Base, Container]] = underlying.map(_.typeMap(f))
      Right(bf().++=(x).result())
    }

    def iterator: Iterator[T] = underlying.iterator
    def apply(idx: Int): T = underlying.apply(idx)
    def length: Int = underlying.length

    val typeName: String = implicitly[TypeName[T]].typeName + "[]"
  }

}
