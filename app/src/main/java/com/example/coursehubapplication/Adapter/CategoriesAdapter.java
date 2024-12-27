package com.example.coursehubapplication.Adapter;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursehubapplication.DashboardScreen.AddCategeryActivity;
import com.example.coursehubapplication.DashboardScreen.DashboardActivity;
import com.example.coursehubapplication.R;
import com.example.coursehubapplication.RoomDatabase.Category;
import com.example.coursehubapplication.RoomDatabase.MyViewModel;
import com.example.coursehubapplication.databinding.CategoryItemBinding;
import com.example.coursehubapplication.databinding.DialogBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Category> categoryList;
    Context context;
    ClickListener clickListener;
    CategoryItemBinding binding;
    AlertDialog dialog_d;


    public CategoriesAdapter(List<Category> categoryList, Context context, ClickListener clickListener) {
        this.categoryList = categoryList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int categoryId = categoryList.get(position).getCategoryId();
        ViewHolder viewHolder = (ViewHolder) holder;
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
                            bundle.putInt("id", categoryList.get(position).getCategoryId());
                            bundle.putString("name", categoryList.get(position).getCategoryName());
                            Intent intent = new Intent(context, AddCategeryActivity.class);
                            intent.putExtra("Category", bundle);
                            context.startActivity(intent);
                        }
                        if (itemId == R.id.deleteItem) {
                            MyViewModel viewModel = new ViewModelProvider((DashboardActivity) context).get(MyViewModel.class);
                            AlertDialog.Builder builder = new AlertDialog.Builder((DashboardActivity) context);
                            DialogBinding binding = DialogBinding.inflate(LayoutInflater.from(context), null, false);
                            builder.setView(binding.getRoot());
                            binding.titeleTV.setText("Confirmation");
                            binding.teMassage.setText("Are you sure you want to delete this category?");
                            binding.selectCategory.setText("Please select category to assign courses?");
                            binding.actionBt.setText("OK");
                            List<String> categoryNames = new ArrayList<>();
                            Category categoryToDelete = categoryList.get(position);
                            for (Category category : categoryList) {
                                if (!category.getCategoryName().equals(categoryToDelete.getCategoryName())) {
                                    categoryNames.add(category.getCategoryName());
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categoryNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.categorySpenner.setAdapter(adapter);

                            binding.actionBt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String selected = binding.categorySpenner.getSelectedItem().toString();

                                    if (selected.isEmpty()) {
                                        Toast.makeText(context, "Please Select Category", Toast.LENGTH_SHORT).show();
                                    } else {
                                        int selectedCategoryId = getCategoryIdByName(selected);
                                        if (selectedCategoryId != -1) {
                                            // التحديث والحذف
                                            viewModel.updateCoursesFromCategory(categoryList.get(position).getCategoryId(), selectedCategoryId);
                                            viewModel.deleteCategory(categoryList.get(position));
                                            dialog_d.dismiss();
                                        } else {
                                            Toast.makeText(context, "Invalid category", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                            binding.imImageIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_d.dismiss();
                                }
                            });

                            dialog_d = builder.create();
                            dialog_d.setCancelable(true);
                            dialog_d.show();


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

        public ViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());//نعرف البيانات في تصميم

            this.binding = binding;

        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface ClickListener {
        void onClick(int category);
    }

    private int getCategoryIdByName(String categoryName) {
        for (Category category : categoryList) {
            if (category.getCategoryName().equals(categoryName)) {
                return category.getCategoryId();
            }
        }
        return -1;
    }
}
