package org.survey.model.file;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.survey.repository.EntityComparator;

public class FileComparator extends EntityComparator<File, Long> {

	@Override
	public int compare(File file1, File file2) {
		if (file1 == file2) {
			return 0;
		}
		if (file1 == null) {
			return -1;
		}
		if (file2 == null) {
			return 1;
		}
		return new CompareToBuilder()
				.append(file1.getFilename(), file2.getFilename())
				.append(file1.getMimeType(), file2.getMimeType())
				.append(file1.getCreateTime(), file2.getCreateTime())
				.append(file1.getContent(), file2.getContent())
//				.append(file1.getOwner(), file2.getOwner())
				.append(file1.getUrl(), file2.getUrl())
				.toComparison();
	}
}
