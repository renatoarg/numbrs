package job.interview.borna.ui.edititem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import job.interview.borna.R

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_edit_item) as NavHostFragment
    }
}