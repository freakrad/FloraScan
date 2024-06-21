package com.example.florascan.ui.save

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.florascan.MainActivity
import com.example.florascan.R
import com.example.florascan.databinding.ItemSaveBinding
import com.example.florascan.helper.PredictionsItem
import com.example.florascan.result.Result

class SaveAdapter(private val viewModel: SaveViewModel) :
    ListAdapter<PredictionsItem, SaveAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, viewModel, this)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val save = getItem(position)

        if (save != null) {
            holder.bind(save)
        }

    }


    class MyViewHolder(
        private val binding: ItemSaveBinding,
        private val viewModel: SaveViewModel,
        private val adapter: SaveAdapter,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(save: PredictionsItem) {
            with(binding) {
                tvItemPenyakit.text = save.predictionData
                tvScore.text = save.predictionScore.toString()
                tvItemDate.text = save.timestamp

                //nambahin ini nyoba bisa masukin img ke histori apa kg
                // Decode Base64 string to a Bitmap and set it to the ImageView
                Glide.with(itemView.context)
                    .load(save.urlImage)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(imgPoster)
                //sampe sini doang, misal kg bisa hapus dh wkwkkw

                ivDelete.setOnClickListener {
                    if (save.id != null) {
                        viewModel.deleteHistory(save.id).observe(itemView.context as MainActivity) {
                            if (it != null) {
                                Toast.makeText(
                                    itemView.context,
                                    "Berhasil dihapus",
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.getHistory()
                                    .observe(itemView.context as MainActivity) { save ->
                                        if (save != null) {
                                            when (save) {
                                                is Result.Error -> Toast.makeText(
                                                    itemView.context,
                                                    save.error,
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                Result.Loading -> null
                                                is Result.Success -> {
                                                    adapter.submitList(save.data.predictions)
                                                    adapter.notifyDataSetChanged()
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PredictionsItem>() {
            override fun areItemsTheSame(
                oldItem: PredictionsItem,
                newItem: PredictionsItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PredictionsItem,
                newItem: PredictionsItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}