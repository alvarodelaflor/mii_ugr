package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import models.Data

import scala.concurrent.{ Future, ExecutionContext }

/**
 * A repository for data.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class DataRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  /**
   * Here we define the table. It will have a name of data
   */
  private class dataTable(tag: Tag) extends Table[Data](tag, "data") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def name = column[String]("name")

    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the Data object.
     *
     * In this case, we are simply passing the id, name and page parameters to the Data case classes
     * apply and unapply methods.
     */
    def * = (id, name) <> ((Data.apply _).tupled, Data.unapply)
  }

  /**
   * The starting point for all queries on the data table.
   */
  private val data = TableQuery[dataTable]

  /**
   * Create a Data with the given name and age.
   *
   * This is an asynchronous operation, it will return a future of the created Data, which can be used to obtain the
   * id for that Data.
   */
  def create(name: String): Future[Data] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (data.map(p => (p.name))
      // Now define it to return the id, because we want to know what id was generated for the Data
      returning data.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((nameAge, id) => Data(id, nameAge))
      // And finally, insert the Data into the database
      ) += (name)
  }

  /**
   * List all the data in the database.
   */
  def list(): Future[Seq[Data]] = db.run {
    data.result
  }
}
