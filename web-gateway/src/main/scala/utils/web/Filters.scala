package utils.web

import play.api.http.HttpFilters
import play.api.mvc.EssentialFilter
import play.filters.gzip.GzipFilter

/**
 * Provides filters.
 */
class Filters(gzipFilter: GzipFilter) extends HttpFilters {
  override def filters: Seq[EssentialFilter] = Seq(gzipFilter)
}

