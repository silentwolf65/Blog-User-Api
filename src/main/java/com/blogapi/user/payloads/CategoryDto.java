package com.blogapi.user.payloads;

import java.util.Comparator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto implements Comparable<CategoryDto> {

	private Integer categoryId;
	private String categoryTitle;
	private String categoryDescription;

	// Method to compare Objects for sorting
	@Override
	public int compareTo(CategoryDto o) {
		return Comparator.comparingInt(CategoryDto::getCategoryId).compare(this, o);

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDto other = (CategoryDto) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		return true;
	}

}
