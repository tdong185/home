package lab1;

import java.util.*;

public class Linker {

	public static void main(String[] args) {

		ArrayList <Module> allMods = new ArrayList <Module> ();
		Scanner scanner = new Scanner(System.in);
		int numMods = scanner.nextInt();		

		HashMap<String, Integer> defsGlobal = new HashMap<String, Integer>();

		//offsets to calculate base adds
		ArrayList <Integer> offsets = new ArrayList <Integer>();
		offsets.add(0);
 
		//keep track of use and definitions

		HashMap<Integer,String> usedNotDef = new HashMap<Integer,String>();
		HashMap<String,String> defButNotUsed = new HashMap<String,String>();
		ArrayList<String> allUsed = new ArrayList<String>();

		//errror and warning checks
		ArrayList<Integer> RTooBig = new ArrayList<Integer>();
		ArrayList<Integer> ATooBig300Limit = new ArrayList<Integer>();
		ArrayList<Integer> MultipleSymSameAddress = new ArrayList<Integer>();
		ArrayList<String> MultipleDefs = new ArrayList<String>();

		HashMap<Integer,Integer> addressMaster = new HashMap<Integer,Integer>();

		for (int i = 0; i < numMods; i++) {

			HashMap<String, Integer> defs = new HashMap<String, Integer>(); 
			HashMap<String, Integer> uses = new HashMap<String, Integer>(); 
			ArrayList <String> addresses = new ArrayList <String> ();

			int numDef = scanner.nextInt();
			for (int defCounter=0; defCounter < numDef; defCounter++) {
				String key = scanner.next();
				Integer value = (scanner.nextInt());

				int offset = 0;
				for (int offsetCounter = 0; offsetCounter < allMods.size();offsetCounter++) {
					offset += allMods.get(offsetCounter).addresses.size();
				}

				defs.put(key, value+offset);
				defsGlobal.put(key,value+offset);
			}

			ArrayList<Integer> alreadyMapped = new ArrayList<Integer>();
			
			int numUse = scanner.nextInt();
			for (int useCounter=0; useCounter < numUse; useCounter++) {
				String key = scanner.next();
				Integer value = scanner.nextInt();
				
				if (alreadyMapped.contains(value)) {
					uses.values().remove(value);
					MultipleSymSameAddress.add(offsets.get(i)+value);
				} 
				
				alreadyMapped.add(value);
				uses.put(key, value);
				allUsed.add(key);
			}

			int numAddresses = scanner.nextInt();
			for (int addressCounter=0; addressCounter < numAddresses; addressCounter++) {
				String tempAddress = scanner.next() + " " + scanner.next();
				addresses.add(tempAddress);				
			}
			offsets.add(offsets.get(i)+numAddresses);

			Module mod = new Module(defs, uses, addresses);
			allMods.add(mod);		
		}

		scanner.close();

		//Pass 2

		for (int i = 0; i < numMods; i++) {

			Module currentMod = allMods.get(i);
			for (int j = 0; j < currentMod.addresses.size(); j++) {

				String currentAddressString = (String)currentMod.addresses.get(j);


				if(currentAddressString.startsWith("I")) {
					String[] addressSplit = currentAddressString.split(" ");
					int adrs = Integer.parseInt(addressSplit[1]);
					currentMod.addresses.set(j, Integer.toString(adrs));					
				}


				else if(currentAddressString.startsWith("R")) {
					String[] addressSplit = currentAddressString.split(" ");
					int adrs = Integer.parseInt(addressSplit[1]);

					int opcode = adrs/1000*1000;
					int actualAdrs = adrs%1000;

					if (actualAdrs > currentMod.addresses.size()) {
						actualAdrs = 0;
						RTooBig.add(offsets.get(i)+j); // machine word limit error check // what to add to RTooBig array?
					}
					adrs = opcode+actualAdrs + offsets.get(i); // add offset
					currentMod.addresses.set(j, Integer.toString(adrs));	
				}

				else if(currentAddressString.startsWith("A")) {
					String[] addressSplit = currentAddressString.split(" ");
					int adrs = Integer.parseInt(addressSplit[1]);

					int opcode = adrs/1000*1000;
					int actualAdrs = adrs%1000;

					if (actualAdrs > 300) {
						actualAdrs = 299;
						ATooBig300Limit.add(offsets.get(i)+j); // machine word limit error check
					}
					adrs = opcode+actualAdrs;
					currentMod.addresses.set(j, Integer.toString(adrs));	

				}

				else {
					String[] addressSplit = currentAddressString.split(" ");
					int adrs = Integer.parseInt(addressSplit[1]);

					//usedNotDefined ERROR CHECK!!!!


					currentMod.addresses.set(j, Integer.toString(adrs));	

				}


			}


		}




		for (int i = 0 ; i <numMods; i++) {
			for (int j = 0; j < allMods.get(i).addresses.size();j++) {
				int counter = offsets.get(i)+j;
				int adrs = Integer.parseInt((String)allMods.get(i).addresses.get(j));

				if (addressMaster.containsKey(offsets.get(i)+j)) {
					MultipleSymSameAddress.add(offsets.get(i)+j);
				}

				addressMaster.put(counter,adrs);
			}
		}






		for (int i = 0 ; i < numMods; i++) {

			Module mod = allMods.get(i);
			ArrayList adrs = mod.addresses;

			for (String symbol : mod.uses.keySet()) {



				String key = symbol;
				int value = mod.uses.get(key);
		
				while (Integer.parseInt((String) adrs.get(value))%1000 != 777) {
					int temp = Integer.parseInt((String)adrs.get(value))%1000;

					if(!defsGlobal.keySet().contains(key)) {
						String message= " Error: " + key + " is not defined; 111 used.";
						usedNotDef.put(offsets.get(i)+value, message);
						int newAddress = Integer.parseInt((String)adrs.get(value))/1000*1000 + 111;

						adrs.set(value, newAddress);
						value=temp;

					} else {
						int newAddress = defsGlobal.get(key);
						int addrs = Integer.parseInt((String)adrs.get(value))/1000*1000+newAddress; 
						adrs.set(value, addrs);
						value = temp;
					}
				}

				if(!defsGlobal.keySet().contains(key)) {
					String message = " Error: " + key + " is not defined; 111 used.";
					usedNotDef.put(offsets.get(i)+value, message);
					int newAddress = Integer.parseInt((String)adrs.get(value))/1000*1000 + 111;
					adrs.set(value, newAddress);

				} else {
					int newAddress = defsGlobal.get(key);
					int addrs = Integer.parseInt((String)adrs.get(value))/1000*1000+newAddress; 
					adrs.set(value, addrs);
				}

			}
		}

		HashMap <String, Integer>isDefined = new HashMap();
		for (int i = 0; i<numMods; i++) {
			Module mod = allMods.get(i);
			for (String symbol : mod.definitions.keySet()) {

				if(!(allUsed.contains((String)symbol))) {
					String message = "Warning: "+symbol+" was defined in module " +i+" but never used.";
					defButNotUsed.put(symbol, message);
				}

				if(isDefined.containsKey(symbol)) {
					MultipleDefs.add(symbol);
				}

				int adrs =mod.definitions.get(symbol);
				isDefined.put(symbol, adrs);
			}
		}








		//print
		System.out.println("Symbol Table");
		for (String key: isDefined.keySet()) {



			if(MultipleDefs.contains(key)) {
				System.out.println(key + " = " + isDefined.get(key) + " Error: This variable is multiply defined; last value used.");
			}


			else {
				System.out.println(key + " = " + isDefined.get(key));
			}

		}

		System.out.println();
		System.out.println("Memory Map");
		for (int i =0; i < numMods; i++) {
			Module mod = allMods.get(i);
			for (int j = 0; j < mod.addresses.size();j++) {
				int counter = offsets.get(i) + j;

				String message = counter + ": "+ mod.addresses.get(j);
				if(RTooBig.contains(counter)) {
					message+= " Error: Type R address exceeds module size; 0 (relative) used.";
				}
				if(ATooBig300Limit.contains(counter)) {
					message+=" Error: A type address exceeds machine size; max legal value used";
				}
				if (usedNotDef.containsKey(counter)) {
					message += usedNotDef.get(counter);
				}
				if (MultipleSymSameAddress.contains(counter)) {
					message += " Error: Multiple symbols used here; last one used.";
				}

				System.out.println(message);

			}

		}

		System.out.println();
		
		for (String key: isDefined.keySet()) {
			if (defButNotUsed.containsKey(key)) {
				System.out.println(defButNotUsed.get(key));
			}
		}


	}

}




