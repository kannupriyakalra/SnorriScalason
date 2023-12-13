/*
 * Copyright 2023 ScalaBridge London
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package snorri

import cats.effect.IO
import io.circe.Json
import krop.all.*
import krop.route.InvariantEntity
import org.http4s.headers.`Content-Type`
import org.http4s.syntax.all.mediaType
import org.http4s.circe._

object Snorri {
  val json: InvariantEntity[Json] =
    Entity(
      CirceEntityDecoder.circeEntityDecoder,
      CirceEntityEncoder.circeEntityEncoder
    )

  val route =
    Route(
      Request.get(Path.root / "echo" / Param.string),
      Response.ok(json/*.withContentType(`Content-Type`(mediaType"application/json"))*/)
    ).handle(param => Json.fromString(param))
}
