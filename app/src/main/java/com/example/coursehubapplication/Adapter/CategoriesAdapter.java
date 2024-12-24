package com.example.coursehubapplication.Adapter;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.AddCategeryActivity;
import com.example.coursehubapplication.DashboardActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.CategoryItemBinding;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Category> categoryList;
    Context context;
    ClickListener clickListener;
    CategoryItemBinding binding;

    public CategoriesAdapter(List<Category> categoryList, Context context, ClickListener clickListener) {
        this.categoryList = categoryList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int categoryId = categoryList.get(position).getCategoryId();
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.binding.nameCategoryTv.setText(categoryList.get(position).getCategoryName());
        viewHolder.binding.moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), viewHolder.binding.moreIcon);
                popupMenu.inflate(R.menu.edite_delete_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.editeItem) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id",categoryList.get(position).getCategoryId());
                            bundle.putString("name", categoryList.get(position).getCategoryName());
                            Intent intent = new Intent(context, AddCategeryActivity.class);
                            intent.putExtra("Category", bundle);
                            context.startActivity(intent);
                        } if (itemId == R.id.deleteItem) {
                            MyViewModel viewModel = new ViewModelProvider((DashboardActivity) context).get(MyViewModel.class);


                            AlertDialog.Builder builder = new AlertDialog.Builder((DashboardActivity) context);
                            builder.setTitle("Confirmation");
                            builder.setMessage("Are you sure you want to delete this category?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText((DashboardActivity) context, "category deleted", Toast.LENGTH_SHORT).show();
                                    viewModel.categoryDelete(categoryList.get(position));
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText((DashboardActivity) context, "canceled", Toast.LENGTH_SHORT).show();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.setCancelable(true);
                            dialog.show();




                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(categoryId);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CategoryItemBinding binding;
        ImageView more;
        public ViewHolder(CategoryItemBinding binding ) {
            super(binding.getRoot());//نعرف البيانات في تصميم

            this.binding=binding;

        }}

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public interface ClickListener {
        void onClick(int category);
    }
}
