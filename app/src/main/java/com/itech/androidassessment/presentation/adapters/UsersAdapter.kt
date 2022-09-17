import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itech.androidassessment.R
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.model.User
import com.itech.androidassessment.utils.ClickListener
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter (val myClickListener: ClickListener) : RecyclerView.Adapter<UsersAdapter.Holder>(){
    var userlist: List<DatabaseUser> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }
    class Holder (itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindItem(user: DatabaseUser) {
            itemView.findViewById<TextView>(R.id.tv_fullname).text = user.name
            itemView.findViewById<TextView>(R.id.tv_username).text = user.username
            itemView.findViewById<TextView>(R.id.tv_email).text = user.email
            var profileImg:CircleImageView = itemView.findViewById(R.id.profile_image)

            Glide
                .with(profileImg.context)
                .load(user?.thumbnail)
                .centerCrop()
                .placeholder(R.drawable.ic_profile_img_placeholder)
                .into(profileImg);

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val x: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_users_item_layout,parent,false)

        return Holder(x)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Holder(holder.itemView).bindItem(userlist[position])
        holder.itemView.findViewById<ConstraintLayout>(R.id.parent_view).setOnClickListener{
            myClickListener.onItemClick(it,userlist[position])
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}