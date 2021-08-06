package com.company;

import java.util.HashMap;

/**
 * This class is a basket
 */
public class BasketInformations {

	// This static variables doesn't have a reason to be static, as there is no static method that could
	// make use of it neither a reason that all classes of BasketInformations share the same contents.
	
	// Also localProducts coudl be better initialized in a constructor, where it's params are passed an so initialized
	
	
	// The product of the basket
	// variable name is not descritpitve of what it holds, or for what it stands for.
	// a name as productPriceMap would be more descriptive
	static HashMap<String, Integer> map = new HashMap<String, Integer>();

	static String[] localProducts = new String[]{"123", "222", "44", "657"};

	// The fact that the basket has promo code
	private static boolean codeDePromotion = false;
	
	private final int MAX_PRODUCTS = 50;

	public void addProductToBasket(String product, Integer price, boolean isPromoCode) {
		if (isPromoCode) {
			codeDePromotion = true;
		} else {
			map.put(product, price);
		}
	}

	public Long getBasketPrice(boolean inCents) {
		Integer v = 0;
		for (String s : map.keySet()) { // variable s could be renamed to 'product' to ease readability
			v += map.get(s); // variable 'v' could be renamed to "totalAmount"
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
	
	// the only using buyBasket is resetBasket(); So i'd rather move map.clear() inside resetBasket.
	// Moreove buybasket method with a map.clear method inside is confusing. The name of the method and
	// what it does leads to confusion.
	public void buyBasket() {
		map.clear();
	}

	public boolean isBasketContains(String produit) {
		boolean found = false;
		for (String s : map.keySet()) { // variable s, could be renamed as prodcut
			// if (s == produit) found = true;  If it's already found, I'd suggest to set 'return true'
			if (s == produit) return true
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
		//if(max > 50){ // this 50 could be a static variable that gaves more context of what it means
		if(max > MAX_PRODUCTS){ 
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
