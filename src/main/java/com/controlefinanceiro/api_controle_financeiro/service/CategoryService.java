package com.controlefinanceiro.api_controle_financeiro.service;

import com.controlefinanceiro.api_controle_financeiro.dto.request.CategoryRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.CategoryResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.CategoryEntity;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import com.controlefinanceiro.api_controle_financeiro.mapper.CategoryMapper;
import com.controlefinanceiro.api_controle_financeiro.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {

        UserEntity user = getAuthenticatedUser();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(request.getName())
                .valueType(request.getValueType())
                .userEntity(user)
                .build();

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

        return categoryMapper.categoryEntityToCategoryResponse(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategoriesForUser() {
        UserEntity user = getAuthenticatedUser();
        List<CategoryEntity> categories = categoryRepository.findByUserEntity(user);
        return categoryMapper.categoryEntityListToCategoryResponseList(categories);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer id) {
        UserEntity user = getAuthenticatedUser();
        CategoryEntity categoryEntity = findCategoryByIdAndValidateOwnership(id, user);
        return categoryMapper.categoryEntityToCategoryResponse(categoryEntity);
    }

    @Transactional
    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        UserEntity user = getAuthenticatedUser();

        CategoryEntity categoryEntity = findCategoryByIdAndValidateOwnership(id, user);

        categoryEntity.setName(request.getName());
        categoryEntity.setValueType(request.getValueType());

        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

        return categoryMapper.categoryEntityToCategoryResponse(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        UserEntity user = getAuthenticatedUser();
        CategoryEntity categoryEntity = findCategoryByIdAndValidateOwnership(id, user);

        if (!categoryEntity.getTransactionEntities().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir a categoria, pois existem transações associadas a ela.");
        }

        categoryRepository.delete(categoryEntity);
    }

    private CategoryEntity findCategoryByIdAndValidateOwnership(Integer categoryId, UserEntity user) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        if (!category.getUserEntity().getId().equals(user.getId())) {
            throw new SecurityException("Acesso negado. A categoria não pertence ao usuário.");
        }
        return category;
    }

    private UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity)) {
            throw new UsernameNotFoundException("Usuário não autenticado.");
        }
        return (UserEntity) authentication.getPrincipal();
    }
}