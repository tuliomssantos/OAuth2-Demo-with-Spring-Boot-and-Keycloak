'use client'

import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import * as z from 'zod'

import { Button } from '@/components/ui/button'
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { loginWithUsernameAndPassword } from '@/lib/data/mutations/login'

const formSchema = z.object({
  username: z.string().min(2).max(50),
  password: z.string().min(3).max(50),
})

export default function Page() {
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: '',
      password: '',
    },
  })

  const onSubmit = async ({
    username,
    password,
  }: z.infer<typeof formSchema>) => {
    await loginWithUsernameAndPassword({
      username,
      password,
    })
  }

  // http://localhost:2727/realms/tuliomssantos/broker/google/login?client_id=tuliomssantos-api&tab_id=PceId-XeCWU&session_code=XB1TgUd1guSUOqn6LWLQEO9UJAhbSfqshc58wxCE3Lw

  return (
    <div className="flex justify-center items-center h-screen">
      <div>
        <h1 className="text-3xl font-semibold tracking-tight transition-colors mb-4">
          Please Sign In
        </h1>

        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Username</FormLabel>
                  <FormControl>
                    <Input placeholder="username" {...field} />
                  </FormControl>
                  <FormDescription>
                    This is your public display name.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input placeholder="********" type="password" {...field} />
                  </FormControl>
                  <FormDescription>
                    Your password must be at least 8 characters.
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Button type="submit">Submit</Button>
          </form>
        </Form>
      </div>
    </div>
  )
}

// import Image from 'next/image'
// import Link from 'next/link'

// export default function Page() {
//   return (
//     <div className="flex justify-center items-center h-screen">
//       <div className="text-center">
//         <h1 className="text-3xl font-semibold tracking-tight transition-colors mb-4">
//           Login
//         </h1>

//         <Link
//           href={`${process.env.NEXT_PUBLIC_API_URL}/oauth2/authorization/google`}
//         >
//           <button className="px-4 py-2 border flex gap-2 border-slate-200 dark:border-slate-700 rounded-lg text-slate-700 dark:text-slate-200 hover:border-slate-400 dark:hover:border-slate-500 hover:text-slate-900 dark:hover:text-slate-300 hover:shadow transition duration-150">
//             <Image
//               width={24}
//               height={24}
//               className="w-6 h-6"
//               src="https://www.svgrepo.com/show/475656/google-color.svg"
//               loading="lazy"
//               alt="google logo"
//             />
//             <span>Login with Google</span>
//           </button>
//         </Link>
//       </div>
//     </div>
//   )
// }
