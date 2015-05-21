package test;

import algorithms.statistics.Statistics;

public class Test {

	public static void main(String[] args) {
		StateProducer s = new StateProducer(new Statistics());
		StateJudger sj = new StateJudger();
		for (int a = 8; a >= 2; a--) {
			StateProducer.PERSON_NUM = a;
			s.initPerson();
			for (int k = StateProducer.BEGIN; k <= StateProducer.END; k++) {
				for (int t = StateProducer.BEGIN; t <= StateProducer.END; t++) {
					for (int h = 0; h < StateProducer.KIND; h++) {
						if (t == k) {
							if (h == 1) {
								continue;
							}
						}
						s.initHold(k, t, h);
						for (int i = 0; i < StateProducer.TIMES; i++) {
							s.initCards();
							int win = sj.getResult(s.getPokers());
							// System.out.println(win);
							s.setResult(win + 1);
							s.reset();
						}
					}
				}
			}
		}
		s.gameover();
	}

}
