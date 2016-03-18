package net.estinet.ClioteSky.network.protocol.input;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.estinet.ClioteSky.Category;
import net.estinet.ClioteSky.Cliote;
import net.estinet.ClioteSky.ClioteSky;
import net.estinet.ClioteSky.exceptions.CreationCategoryNotKnownException300;
import net.estinet.ClioteSky.exceptions.IncorrectArgumentsException101;
import net.estinet.ClioteSky.exceptions.NameNotKnownException201;
import net.estinet.ClioteSky.exceptions.PasswordIncorrectException900;
import net.estinet.ClioteSky.exceptions.RegisterFirstException901;
import net.estinet.ClioteSky.network.protocol.InputPacket;
import net.estinet.ClioteSky.network.protocol.Packet;
import net.estinet.ClioteSky.network.protocol.output.OutputError;

public class InputChange extends InputPacket implements Packet {

	public InputChange(){
		super.setName("change");
		super.setDescription("The change function changes a Cliote's setting. The IP of the client will be changed as well as the category. The name cannot be changed.");
		super.setUsage("change [Cliote Name] [Category] [Password]");
	}

	@Override
	public void run(List<String> args, Cliote sender) {
		if(sender.getName().equals("unknown") || !sender.getIsOnline()){
			try{
				throw new RegisterFirstException901();
			}
			catch(RegisterFirstException901 e){
				e.printStackTrace();
				OutputError oe = new OutputError();
				oe.run(Arrays.asList("901"), sender);
			}
		}
		else{
			if(args.size() != 3){
				try{
					throw new IncorrectArgumentsException101();
				}
				catch(IncorrectArgumentsException101 e){
					e.printStackTrace();
					OutputError oe = new OutputError();
					oe.run(Arrays.asList("101"), sender);
				}
			}
			else{
				String clioteName = args.get(0), category = args.get(1), password = args.get(2);
				if(ClioteSky.getCliote(clioteName) == null){
					try{
						throw new NameNotKnownException201();
					}
					catch(NameNotKnownException201 e){
						e.printStackTrace();
						OutputError oe = new OutputError();
						oe.run(Arrays.asList("201"), sender);
					}
				}
				else{
					boolean bool = false;
					for(Category categorys : ClioteSky.categories){
						if(categorys.getName().equals(category)){
							bool = true;
						}
					}
					if(bool){
						if(!ClioteSky.getCliote(clioteName).getPassword().equals(password)){
							try{
								throw new PasswordIncorrectException900();
							}
							catch(PasswordIncorrectException900 e){
								e.printStackTrace();
								OutputError oe = new OutputError();
								oe.run(Arrays.asList("900"), sender);
							}
						}
						else{
							for(int i = 0; i < ClioteSky.categories.size(); i++){
								for(int it = 0; it < ClioteSky.categories.get(i).getCliotes().size(); it++){
									if(ClioteSky.categories.get(i).getCliotes().get(it).getName().equals(clioteName)){
										List<Cliote> cliotes = ClioteSky.categories.get(i).getCliotes();
										Cliote cliote = ClioteSky.categories.get(i).getCliotes().get(it);
										cliotes.remove(it);
										ClioteSky.categories.get(i).setCliotes(cliotes);
										File getRidOf = new File("./Cliotes/" + ClioteSky.categories.get(i).getName() + "/" + cliote.getName());
										getRidOf.delete();
										for(int iterator = 0; iterator < ClioteSky.categories.size(); iterator++){
											if(ClioteSky.categories.get(iterator).getName().equals(category)){
												ClioteSky.categories.get(iterator).addCliote(cliote);
												ClioteSky.println("Successfully changed categories for Cliote " + cliote.getName());
											}
										}
										break;
									}
								}
							}
						}
					}
					else{
						try{
							throw new CreationCategoryNotKnownException300();
						}
						catch(CreationCategoryNotKnownException300 e){
							e.printStackTrace();
							OutputError oe = new OutputError();
							oe.run(Arrays.asList("300"), sender);
						}
					}
				}
			}
		}
	}

}
