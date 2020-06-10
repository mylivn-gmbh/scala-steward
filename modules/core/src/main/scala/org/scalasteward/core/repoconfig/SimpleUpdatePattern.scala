package org.scalasteward.core.repoconfig

import cats.implicits._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import org.scalasteward.core.data.{GroupId, Update}

final case class SimpleUpdatePattern(
    groupId: GroupId,
    artifactId: Option[String]
)

object SimpleUpdatePattern {
  def findMatch(
      patterns: List[SimpleUpdatePattern],
      update: Update.Single
  ): List[SimpleUpdatePattern] = {
    val byGroupId = patterns.filter(_.groupId === update.groupId)
    byGroupId.filter(_.artifactId.forall(_ === update.artifactId.name))
  }

  implicit val simpleUpdatePatternDecoder: Decoder[SimpleUpdatePattern] =
    deriveDecoder

  implicit val simpleUpdatePatternEncoder: Encoder[SimpleUpdatePattern] =
    deriveEncoder
}
