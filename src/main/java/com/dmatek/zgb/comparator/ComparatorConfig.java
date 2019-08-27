package com.dmatek.zgb.comparator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComparatorConfig {
	@Bean
	public AccessViewComparator getAccessViewComparator() {
		return new AccessViewComparator();
	}
	@Bean
	public AscendingWarmComparator getAscendingWarmComparator() {
		return new AscendingWarmComparator();
	}
	@Bean
	public DescAccessComparator getDescAccessComparator() {
		return new DescAccessComparator();
	}
	@Bean
	public DescendingWarmComparator getDescendingWarmComparator() {
		return new DescendingWarmComparator();
	}
	@Bean
	public AscInOutRecordComparator getAscInOutRecordComparator() {
		return new AscInOutRecordComparator();
	}
	@Bean
	public IComparatorTool getComparatorTool(AccessViewComparator accessViewComparator,
			AscendingWarmComparator ascendingWarmComparator, DescAccessComparator descAccessComparator,
			DescendingWarmComparator descendingWarmComparator, AscInOutRecordComparator ascInOutRecordComparator){
		return new ComparatorTool(accessViewComparator,
				ascendingWarmComparator, descAccessComparator,
				descendingWarmComparator, ascInOutRecordComparator);
	}
}
