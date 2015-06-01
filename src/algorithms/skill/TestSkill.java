
package algorithms.skill;

import bean.Action;
import bean.Color;
import bean.Poker;
import framework.record.ISceneReader;

public class TestSkill implements ISkill{
	private ISceneReader sceneReader;
	private int point0, point1;
	private Color color0, color1;

	public Action getSkillAction(ISceneReader sceneReader) {
		this.sceneReader = sceneReader;
		Poker holdPoker[] = sceneReader.hold();
		point0 = holdPoker[0].getPoint();
		color0 = holdPoker[0].getColor();
		point1 = holdPoker[1].getPoint();
		color1 = holdPoker[1].getColor();

		if (leastPriority()) {
			if (isCanCheck()) {
				return Action.check;
			}
			return Action.fold;
		}

		if (priority1()) {
			if (onlyAllin()) {
				return Action.all_in;
			}
			if (sceneReader.raiseCount() == 0) {
				Action tempAction = Action.raise;
				tempAction.setNum(2);// 无限盲注，加注必须是两倍
				return tempAction;
			} else if (isCanCheck()) {
				return Action.check;
			} else {
				return Action.call;
			}
		}

		if (priority2()) {
			if (isCanCheck()) {
				return Action.check;
			} else if (onlyAllin()) {
				if (sceneReader.getAlivePersonCount() <= 5) {
					return Action.all_in;
				}
				return Action.fold;
			} else {
				return Action.call;
			}
		}
		if (priority3()) {
			if (isCanCheck()) {
				return Action.check;
			} else if (onlyAllin()) {
				if (sceneReader.getAlivePersonCount() <= 3) {
					return Action.all_in;
				}
				return Action.fold;
			} else {
				return Action.call;
			}
		}

		if (priority4()) {
			if (isCanCheck()) {
				return Action.check;
			} else if (onlyAllin()) {
				return Action.fold;
			} else {
				if ((sceneReader.callJetton() < sceneReader.getBlind() * 1.35)
						|| sceneReader.person().length <= 4)
					return Action.call;
				else
					return Action.fold;
			}
		}

		// 预防意外
		if (isCanCheck()) {
			return Action.check;
		}
		return Action.fold;

	}

	private boolean onlyAllin() {
		if (sceneReader.lastJetton() <= sceneReader.callJetton()) {
			return true;
		}
		return false;
	}

	private int getPriority() {
		if (leastPriority()) {
			return 0;
		}
		if (priority1()) {
			return 10;
		}

		if (priority2()) {
			return 8;
		}

		return 0;
	}

	// 最低优先级 <9
	private boolean leastPriority() {
		if (point0 < 9 || point1 < 9) {
			return true;
		}
		return false;
	}

	// 第一优先级 AA KK
	private boolean priority1() {
		if (point0 == point1 && point0 >= 13) {
			return true;
		}
		return false;
	}

	// 第二优先级（排除了第一优先级）
	private boolean priority2() {
		// QQ JJ
		if (point0 == point1 && point0 >= 11) {
			return true;
		}

		// 不管颜色的AK KQ AQ
		if (point0 >= 12 && point1 >= 12) {
			return true;
		}
		return false;
	}

	// 第三优先级（排除了第二优先级）
	private boolean priority3() {
		// 1010
		if (point0 == point1 && point0 == 10) {
			return true;
		}

		// 不管颜色的AJ KQ KJ QJ
		if (((point0 > point1 ? point0 : point1) >= 11)
				&& (point0 < point1 ? point0 : point1) >= 11) {
			return true;
		}

		// 同色的J10
		if (color0.equals(color1) && point0 >= 10 && point1 >= 10) {
			if ((Math.abs(point0 - point1) == 1)) {
				return true;
			}
		}

		return false;
	}

	private boolean priority4() {
		if (point0 == point1 && point0 == 9)
			return true;
		int max = point0 > point1 ? point0 : point1;
		int min = point0 < point1 ? point0 : point1;
		if ((max == 14 && min <= 3) || (max >= 13 && min >= 10)) {
			return true;
		}
		return false;
	}

	private boolean isCanCheck() {
		Action availableAction[] = sceneReader.availableAction();
		for (int i = 0; i < availableAction.length; i++) {
			if (availableAction[i].equals(Action.check)) {
				return true;
			}
		}
		return false;
	}
}

