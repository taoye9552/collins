package models

import play.api.db._
import play.api.Play.current

import java.sql._
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.{H2Adapter, MySQLInnoDBAdapter}

/**
 * Wrapper on Play DB object so models don't need an implicit application
 */
object Model {
  val name = "collins"

  lazy val driver = current.configuration.getString("db.%s.driver".format(name)).getOrElse("org.h2.Driver")
  def adapter = driver match {
    case h2 if h2.toLowerCase.contains("h2") => new H2Adapter
    case mysql if mysql.toLowerCase.contains("mysql") => new MySQLInnoDBAdapter
  }

  def initialize() {
    SessionFactory.concreteFactory = Some(
      () => Session.create(DB.getConnection(name), adapter)
    )
  }

}
