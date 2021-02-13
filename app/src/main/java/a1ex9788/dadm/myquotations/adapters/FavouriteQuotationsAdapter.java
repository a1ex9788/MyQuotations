package a1ex9788.dadm.myquotations.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a1ex9788.dadm.myquotations.R;
import a1ex9788.dadm.myquotations.model.Quotation;

public class FavouriteQuotationsAdapter extends RecyclerView.Adapter<FavouriteQuotationsAdapter.ViewHolder> {

    private static List<Quotation> favouriteQuotations;
    private IOnItemClickListener onItemClickListener;

    public FavouriteQuotationsAdapter(List<Quotation> favouriteQuotations, IOnItemClickListener onItemClickListener) {
        this.favouriteQuotations = favouriteQuotations;
        this.onItemClickListener = onItemClickListener;
    }

    public static Quotation getQuotation(int position) {
        return favouriteQuotations.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_list_row, parent, false);
        return new FavouriteQuotationsAdapter.ViewHolder(view, view.findViewById(R.id.textView_quoteText), view.findViewById(R.id.textView_quoteAuthor), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.quoteText.setText(favouriteQuotations.get(position).getQuoteText());
        holder.quoteAuthor.setText(favouriteQuotations.get(position).getQuoteAuthor());
    }

    @Override
    public int getItemCount() {
        return favouriteQuotations.size();
    }

    public interface IOnItemClickListener {

        void onItemClickListener(int position);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quoteText, quoteAuthor;

        public ViewHolder(@NonNull View itemView, TextView quoteText, TextView quoteAuthor, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.quoteText = quoteText;
            this.quoteAuthor = quoteAuthor;

            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClickListener(getAdapterPosition());
            });
        }

    }

}
