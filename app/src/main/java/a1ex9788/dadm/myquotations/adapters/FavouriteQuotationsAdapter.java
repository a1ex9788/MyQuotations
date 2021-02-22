package a1ex9788.dadm.myquotations.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.myquotations.R;
import a1ex9788.dadm.myquotations.model.Quotation;

public class FavouriteQuotationsAdapter extends RecyclerView.Adapter<FavouriteQuotationsAdapter.ViewHolder> {

    public IOnItemClickListener onItemClickListener;
    public IOnItemLongClickListener onItemLongClickListener;
    private List<Quotation> favouriteQuotations;

    public FavouriteQuotationsAdapter(List<Quotation> favouriteQuotations) {
        if (favouriteQuotations == null) {
            this.favouriteQuotations = new ArrayList();
        } else {
            this.favouriteQuotations = favouriteQuotations;
        }
    }

    public Quotation getQuotation(int position) {
        return favouriteQuotations.get(position);
    }

    public void removeQuotation(int position) {
        favouriteQuotations.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAllQuotations() {
        favouriteQuotations.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_list_row, parent, false);
        return new FavouriteQuotationsAdapter.ViewHolder(view, view.findViewById(R.id.textView_quoteText), view.findViewById(R.id.textView_quoteAuthor), onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.quoteText.setText(favouriteQuotations.get(position).getText());
        holder.quoteAuthor.setText(favouriteQuotations.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return favouriteQuotations.size();
    }

    public void setQuotations(List<Quotation> quotations) {
        favouriteQuotations = quotations;

        notifyDataSetChanged();
    }

    public interface IOnItemClickListener {

        void onItemClickListener(int position);

    }

    public interface IOnItemLongClickListener {

        void onItemLongClickListener(int position);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quoteText, quoteAuthor;

        public ViewHolder(@NonNull View itemView, TextView quoteText, TextView quoteAuthor, IOnItemClickListener onItemClickListener, IOnItemLongClickListener onItemLongClickListener) {
            super(itemView);
            this.quoteText = quoteText;
            this.quoteAuthor = quoteAuthor;

            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClickListener(getAdapterPosition());
            });

            itemView.setOnLongClickListener(v -> {
                onItemLongClickListener.onItemLongClickListener(getAdapterPosition());
                return false;
            });
        }

    }

}
