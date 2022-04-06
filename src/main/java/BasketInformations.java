package com.company;

import java.util.HashMap;

/**
 * This class is a basket
 */
public class BasketInformations {

	// The product of the basket
	static HashMap<String, Integer> map = new HashMap<String, Integer>();

	static String[] localProducts = new String[]{"123", "222", "44", "657"};

	// The fact that the basket has promo code
	private static boolean codeDePromotion = false;

	public void addProductToBasket(String product, Integer price, boolean isPromoCode) {
		if (isPromoCode) {
			codeDePromotion = true;
		} else {
			map.put(product, price);
		}
	}

	public Long getBasketPrice(boolean inCents) {
		Integer v = 0;
		for (String s : map.keySet()) {
			v += map.get(s);
		}
		if (codeDePromotion) {
			v -= 100;
		}
		return inCents ? v * 100 : Long.valueOf(v);
	}

	public void resetBasket() {
		buyBasket();
		codeDePromotion = false;
	}

	public void buyBasket() {
		map.clear();
	}

	public boolean isBasketContains(String produit) {
		boolean found = false;
		for (String s : map.keySet()) {
			if (s == produit) found = true;
		}
		return found;
	}

	public void mixWithBasket(BasketInformations b) {
		map.putAll(b.map);
	}

	public boolean isSendFree() {
		boolean free = false;

		//Contains 3 or more local products
		if(totalLocalProducts(0,0) > 3) {
			free = true;
		}

		//Product exceeds 50
		int max = 0;
		for(String s : map.keySet()){
			if(map.get(s) > max){
				max = map.get(s);
			}
		}
		if(max > 50){
			free = true;
		}

		//Price exceeds 1000,00
		double total = getBasketPrice(true);
		if(total < Integer.valueOf("1000,00") ){
			return free;
		} else {
			return true;
		}
	}

	int totalLocalProducts(int step, int partial){
		if(map.keySet().size() == step){
			return partial;
		}
		String produit = (String) map.keySet().toArray()[step];
		for (String s : localProducts){
			if(produit == s){
				partial = partial + 1;
			}
		}
		return totalLocalProducts(++step, partial);
	}
}
