package org.survey.model.poll;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.survey.repository.EntityComparator;

public class PollComparator extends EntityComparator<Poll, Long> {

	@Override
	public int compare(Poll poll1, Poll poll2) {
		if (poll1 == poll2) {
			return 0;
		}
		if (poll1 == null) {
			return -1;
		}
		if (poll2 == null) {
			return 1;
		}
		return new CompareToBuilder()
		        .append(poll1.getName(), poll2.getName())
		        // TODO uncomment when questions are persisted
//		        .append(poll1.getQuestions(), poll2.getQuestions())
//		        .append(poll1.getQuestions().size(), poll2.getQuestions().size())
//		        .append(poll1.getQuestions().toArray(), poll2.getQuestions().toArray())
		        // TODO what if owner is null?
//				.append(poll1.getOwner().getUsername(), poll2.getOwner().getUsername())
				.toComparison();
	}
}
