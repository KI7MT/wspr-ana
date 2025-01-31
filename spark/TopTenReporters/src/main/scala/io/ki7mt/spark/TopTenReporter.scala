package io.ki7mt.spark

import java.time.LocalDateTime

import org.apache.log4j._

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

// Get The Top Ten Reporters by Count
object TopTenReporter {

  case class Reporter(SpotID: Integer, Timestamp: Integer, Reporter: String)

  // I changed it to Milliseconds as Nanoseconds is a bit to high in percision
  // Also added the Long $et for better readability
  // https://biercoff.com/easily-measuring-code-execution-time-in-scala/
  def time[R](block: => R): R = {
      val t0 = System.currentTimeMillis()
      val result = block  // call the block
      val t1 = System.currentTimeMillis()
      val elapsedTimeMsec: Float = (t1 - t0)
      val elapsedTime: Float = (elapsedTimeMsec / 1000)

      println(f"Query Time : $elapsedTime sec\n")
      result
  }

  // The main entry point
  def main(args: Array[String]) {

    if (args.length <= 0) {
      println("\nInput File Error")
      println("Usage: TopTenReporters <file path to wsprspots-xxxx-xx.csv")
      println("Example: wsprspots-2020-02.csv")
      System.exit(1)
    }

    val csvfile: String = args(0)
    val appname: String = "TopTenReporter"
    val timestamp: String = LocalDateTime.now().toString()
    val description: String = "Returns the Top Ten Reporters Grouped by Count"

    println(s"\nApplication  : $appname")
    println(s"Process File : $csvfile" )
    println(s"Tiimestame   : $timestamp")
    println(s"Description  : $description\n" )
    println("Process Steps for this application")

    // Set the Java Log Leve
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Spark Session: local[*] uses all cores on the local machine
    val spark = SparkSession
      .builder
      .appName("TopTenReporters")
      .master("local[*]")
      .getOrCreate()

    // create the schema
    println("- Creating the Schema")
    val reporterSchema = new StructType()
      .add("SpotID", IntegerType, nullable = false)
      .add("Timestamp", IntegerType, nullable = false)
      .add("Reporter", StringType, nullable = false)
      
    // Read the CSV into the DataSet
    println("- Reading CSV into DataSet")
    import spark.implicits._
    val reporterDS = spark.read
      .schema(reporterSchema)
      .csv(csvfile)
      .as[Reporter]
    
    // Select only the reporter column as that's all we need
      println("- Selecting Reporters")
    val reporter = reporterDS.select("Reporter")

    // Group and count
    println("- GroupBy and Count Reporters")
    val rank = reporter.groupBy("Reporter").count().alias("Count")

    // Sort the resulting dataset by count column descending (default is assending)
    println("- Sort Reporters Descending")
    val sortedResults = rank.sort(desc("Count"))

    // Print results from the dataset
    println("- Query Execution\n")
    time {sortedResults.show(10)}

    // shutdown spark engine
    spark.stop()

  } // END - main method

} // END - TopTenReporterDataset