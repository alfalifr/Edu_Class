package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class Page(var id: String, var name: String,
                var contentSection: FK_M<PageSection>?): Serializable