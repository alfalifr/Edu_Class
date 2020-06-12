package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class PageSection(var id: String?, var content: FK_M<Content>?): Serializable