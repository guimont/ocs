package models



/**
 * Created with IntelliJ IDEA.
 * User: gmo
 * Date: 23/06/14
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
case class IdentCustomer(id: Long, customer: String,host: String,Company: String, Node: String)
case class IdentEngine(id: Long, code: Char, area: Char)
case class Message (idc: IdentCustomer, ide: IdentEngine, data: Any)
