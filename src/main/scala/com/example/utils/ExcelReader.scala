package com.example.utils

import org.apache.poi.ss.usermodel.{Cell, CellType, WorkbookFactory}
import spray.json.{DefaultJsonProtocol, JsObject, JsString, JsValue, JsonFormat, RootJsonFormat}
import java.io.File
import scala.jdk.CollectionConverters._

object ExcelReader extends DefaultJsonProtocol{
  def readExcelAsJson(file: File): List[Map[String, String]] = {
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)

    val rows = sheet.iterator().asScala.toList
    if (rows.isEmpty) return List.empty

    val headers = rows.head.cellIterator().asScala.map(cellToString).toList
    val dataRows = rows.tail

    val result = dataRows.map { row =>
      val cells = row.cellIterator().asScala.map(cellToString).toList
      headers.zipAll(cells, "", "").toMap
    }

    workbook.close()
    result
  }

  private def cellToString(cell: Cell): String = cell.getCellType match {
    case CellType.STRING  => cell.getStringCellValue
    case CellType.NUMERIC => cell.getNumericCellValue.toString
    case CellType.BOOLEAN => cell.getBooleanCellValue.toString
    case _                => ""
  }

  implicit val mapFormat: JsonFormat[Map[String, String]] = new JsonFormat[Map[String, String]] {
    def write(m: Map[String, String]): JsObject = JsObject(m.view.mapValues(JsString(_)).toMap)

    def read(value: JsValue): Map[String, String] = ???
  }

  implicit val listFormat: RootJsonFormat[List[Map[String, String]]] = listFormat[Map[String, String]]
}
