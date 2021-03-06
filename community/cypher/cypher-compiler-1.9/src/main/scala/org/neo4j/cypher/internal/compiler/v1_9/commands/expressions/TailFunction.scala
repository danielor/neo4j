/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v1_9.commands.expressions
import org.neo4j.cypher.internal.compiler.v1_9.symbols.{SymbolTable, AnyCollectionType}
import org.neo4j.cypher.internal.helpers.CollectionSupport
import org.neo4j.cypher.internal.compiler.v1_9.ExecutionContext
import org.neo4j.cypher.internal.compiler.v1_9.pipes.QueryState

case class TailFunction(collection: Expression) extends NullInNullOutExpression(collection) with CollectionSupport {
  def compute(value: Any, m: ExecutionContext)(implicit state: QueryState) = {
    val coll = makeTraversable(value)
    if (coll.size == 0) 
      Seq()
    else 
      coll.tail
  }

  def rewrite(f: (Expression) => Expression) = f(TailFunction(collection.rewrite(f)))

  def children = Seq(collection)

  def calculateType(symbols: SymbolTable) = collection.evaluateType(AnyCollectionType(), symbols)

  def symbolTableDependencies = collection.symbolTableDependencies
}
