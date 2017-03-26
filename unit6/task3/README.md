```Java
class MedicalStaff{}
class Doctor extends MedicalStaff{}
class Nurse extends MedicalStaff{}
class HeadDoctor extends Doctor{}
```

Initialisation | correct | not correct
-------------- | ------- | -----------
`Doctor doctor1 = new Doctor();` | + | 
`Doctor doctor2 = new MedicalStaff();` |  | +
`Doctor doctor3 = new HeadDoctor();` | + | 
`Object object1 = new HeadDoctor();` | + | 
`HeadDoctor doctor5 = new Object();` |  | +
`Doctor doctor6 = new Nurse();` |  | + 
`Nurse nurse = new Doctor();` |  | + 
`Object object2 = new Nurse();` | + | 
`List<Doctor> list1 = new ArrayList<Doctor>();` | + | 
`List<MedicalStaff> list2 = new ArrayList<Doctor>();` |  | + 
`List<Doctor> list3 = new ArrayList<MedicalStaff>();` |  | +
`List<Object> list4 = new ArrayList<Doctor>();` |  | +
`List<Object> list5 = new ArrayList<Object>();` | + | 

Explanations:
- Every object can be referenced by a class that is the object's class or the one of the super classes.
- Also collections classes which is all generics can be referenced by the classes with the same generic type or by the constructions like in the example below.

```Java
List<Doctor> list1 = new ArrayList<MedicalStaff>(); //incorrect

List<? super Doctor> list2 = new ArrayList<MedicalStaff>(); //correct
```
