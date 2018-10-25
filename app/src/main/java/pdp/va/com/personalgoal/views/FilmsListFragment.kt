package pdp.va.com.personalgoal.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import pdp.va.com.personalgoal.DIUtils
import pdp.va.com.personalgoal.adapters.FilmListAdapter
import pdp.va.com.personalgoal.adapters.FilmListAdapter.OnItemClickListener
import pdp.va.com.personalgoal.databinding.FilmsListFragmentBinding
import pdp.va.com.personalgoal.viewmodels.FilmListViewModel

class FilmsListFragment : Fragment() {

    companion object {
        fun newInstance() = FilmsListFragment()
    }

    private lateinit var viewModel: FilmListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FilmsListFragmentBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        val factory = DIUtils.getFilmListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(FilmListViewModel::class.java)

        val itemCLick = object : OnItemClickListener  {
            override fun onItemClick(id: Int) {
                (activity as MainActivity).addFragment(FilmDetailsFragment.newInstance(id))
            }
        }
        val adapter = FilmListAdapter(itemCLick)
        binding.filmList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: FilmListAdapter) {
        viewModel.getFilms().observe(this, Observer { films ->
            if (films != null) adapter.submitList(films)
        })
    }

}