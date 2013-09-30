//
//  HelloWorldViewController.m
//  HelloWorld
//
//  Created by LAN Tian on 27/09/13.
//  Copyright (c) 2013 LAN Tian. All rights reserved.
//

#import "HelloWorldViewController.h"

@interface HelloWorldViewController ()

@end

@implementation HelloWorldViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)showMessage
{
    UIAlertView *helloWorldAlert = [[UIAlertView alloc] initWithTitle:@"My first app" message:@"Hello World" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil, nil];
    
    [helloWorldAlert show];
}

@end
